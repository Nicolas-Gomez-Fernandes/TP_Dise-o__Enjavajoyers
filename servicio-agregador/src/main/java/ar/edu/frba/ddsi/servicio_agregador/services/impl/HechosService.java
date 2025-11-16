package ar.edu.frba.ddsi.servicio_agregador.services.impl;

import ar.edu.frba.ddsi.servicio_agregador.clients.IAPIClient;
import ar.edu.frba.ddsi.servicio_agregador.clients.impl.FuenteDinamicaClient;
import ar.edu.frba.ddsi.servicio_agregador.clients.impl.FuenteEstaticaClient;
import ar.edu.frba.ddsi.servicio_agregador.clients.impl.FuenteProxyClient;
import ar.edu.frba.ddsi.servicio_agregador.exceptions.geoRef.GeoRefException;
import ar.edu.frba.ddsi.servicio_agregador.exceptions.HechoExistenteException;
import ar.edu.frba.ddsi.servicio_agregador.exceptions.ValidationException;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.external.HechoResponseDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.ubiApiGeoRef.UbicacionGeoRefDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.HechoOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.UbicacionDto;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.adapters.IApiGeoRef;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.fuentes.OrigenFuente;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.EvaluadorDeConsenso;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Multimedia;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Ubicacion;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.IFuentesRepository;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.IHechosRepository;
import ar.edu.frba.ddsi.servicio_agregador.services.ICategoriasService;
import ar.edu.frba.ddsi.servicio_agregador.services.IHechosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class HechosService implements IHechosService {
  private ICategoriasService categoriaService;
  private IHechosRepository hechosRepository;
  private IFuentesRepository fuentesRepository;

  private FuenteEstaticaClient estaticaWebClient;

  private EvaluadorDeConsenso evaluadorDeConsenso;

  private IApiGeoRef apiGeoRef;

  public HechosService(IApiGeoRef apiGeoRef, IHechosRepository hechosRepository, IFuentesRepository fuentesRepository, FuenteEstaticaClient estatica, EvaluadorDeConsenso evaluadorDeConsenso, ICategoriasService categoriaService) {
    this.hechosRepository = hechosRepository;
    this.fuentesRepository = fuentesRepository;
    this.estaticaWebClient = estatica;
    this.evaluadorDeConsenso = evaluadorDeConsenso;
    this.categoriaService = categoriaService;
    this.apiGeoRef = apiGeoRef;
  }

  @Override
  public List<Hecho> getHechosByFuentes(List<OrigenFuente> fuentesAsociadas) {
    // TODO: Implementar?
    return List.of();
  }

  @Override
  public Mono<Void> actualizarYCargarNuevosHechos() {
    List<IAPIClient> servicios = List.of(estaticaWebClient);

    return Flux.fromIterable(servicios)
        .doOnNext(client -> log.info("🔄 Procesando cliente: {}", client.getClass().getSimpleName()))
        .flatMap(client -> client.getHechos()
            .onErrorResume(e -> {
              log.error("❌ Error al obtener hechos de {}: {}",
                  client.getClass().getSimpleName(), e.getMessage());
              return Mono.empty();
            }))
        .doOnNext(lista -> log.info("📋 Recibidos {} hechos", lista.size()))
        .concatMap(this::cargarFuentesYCategorias)
        .flatMapIterable(hechos -> hechos)
        .doOnNext(hecho -> log.info("📝 Procesando hecho: {}", hecho.getTitulo()))
        .flatMap(this::actualizarOCrearHecho)
        .then()
        .doOnSubscribe(s -> log.info("🚀 Iniciando actualización de hechos"))
        .doOnTerminate(() -> log.info("✅ Actualización de hechos completada."));
  }

  public Mono<List<HechoResponseDTO>> cargarFuentesYCategorias(List<HechoResponseDTO> hechosDTO) {
    return Mono.fromCallable(() -> {

      // Cargar nuevas fuentes
      hechosDTO.stream().map(HechoResponseDTO::getOrigen)
          .distinct()
          .filter(Objects::nonNull)
          .filter(fDto -> !this.fuentesRepository.existsByFuenteAndSubfuente(fDto.getTipoFuente(), fDto.getFuente()))
          .forEach(fDto -> {
            OrigenFuente nuevaFuente = new OrigenFuente(fDto.getTipoFuente(), fDto.getFuente());
            log.info("Nueva fuente creada: {} [tipo: {}]", nuevaFuente.getSubfuente(), nuevaFuente.getFuente());
            this.fuentesRepository.save(nuevaFuente);
          });

      // Cargar nuevas categorias
      hechosDTO.stream().map(HechoResponseDTO::getCategoria)
          .distinct()
          .filter(Objects::nonNull)
          .forEach(catDto -> {
            this.categoriaService.buscarPorNombreOCrear(catDto);
          });

      return hechosDTO;
    }).publishOn(Schedulers.boundedElastic());
  }

  public Mono<Void> actualizarOCrearHecho(HechoResponseDTO dto) {
    return Mono.fromCallable(() -> {
          Hecho hecho = this.hechosRepository
              .findByExternalIdAndFuente_Fuente(dto.getId(), dto.getOrigen().getTipoFuente())
              .orElseGet(() -> this.crearHecho(dto));
          if (hecho.getId() != null) {
            hecho = this.actualizarDatosHecho(hecho, dto);
          }

          return this.hechosRepository.save(hecho);
        })
        .publishOn(Schedulers.boundedElastic())
        .doOnSuccess(h -> log.info("Hecho guardado: {} ", h.getTitulo()))
        .onErrorResume(HechoExistenteException.class, e -> {
          log.info("Hecho duplicado omitido: {}", e.getMessage());
          return Mono.empty();
        })
        .onErrorResume(e -> {
          log.error("Error general al guardar hecho {}: {}", dto.getTitulo(), e.getMessage());
          return Mono.empty();
        })
        .then();
  }

  private Hecho crearHecho(HechoResponseDTO dto) {
    OrigenFuente origenFuente = fuentesRepository.findBySubfuente(dto.getOrigen().getFuente()).orElseThrow(() -> {
      log.error("Fuente no encontrada para el hecho: {} [{}]", dto.getTitulo(), dto.getOrigen().getFuente());
      return new RuntimeException("Fuente no encontrada: " + dto.getOrigen().getFuente());
    });

    Categoria categoria = categoriaService.buscarPorNombreOCrear(dto.getCategoria());
    Ubicacion ubicacion = new Ubicacion(dto.getLatitud(), dto.getLongitud());

    List<Multimedia> multimedia = dto.getMultimedia() != null
        ? Multimedia.fromDTOList(dto.getMultimedia())
        : List.of();

    //this.cargarProvincia(hecho);

    return Hecho.builder()
        .externalId(dto.getId())
        .fuente(origenFuente)
        .titulo(dto.getTitulo())
        .descripcion(dto.getDescripcion())
        .categoria(categoria)
        .ubicacion(ubicacion)
        //.provincia("") // TODO: completar con georef
        .fechaCreacion(LocalDateTime.now())
        .fechaAcontecimiento(dto.getFecha_hecho())
        .etiquetas(dto.getEtiquetas())
        .eliminado(dto.getEliminado())
        .archivosMultimedia(multimedia)
        .build();
  }

  @Override
  public Hecho getById(Long id) {
    return this.hechosRepository.findById(id).orElseThrow(() -> {
      log.error("Hecho {} no encontrado ", id);
      return new RuntimeException("Hecho no encontrado con id: " + id);
    });
  }


  /*@Override
  public List<Hecho> getAllHechos() { // TODO. Pensar si esto no tendria que estar en ColeccionService
    //return apiClient.stream().flatMap(client -> client.getHechos().stream()).map(ManualHechoMapper::mapHechoToHechoOutputDTO).toList();
    return this.hechosRepository.findAll();
  }

  @Override
  public List<Hecho> getHechosByFuente(OrigenFuente fuente) {
    return this.hechosRepository.findHechosByFuente(fuente);
  }

  @Override
  public List<Hecho> getHechosByFuentes(List<Fuente> fuentesAsociadas) {
    //buscamos las APIs de dichas fuentes
    //List<IAPIClient> apis = fuentesAsociadas.stream().map(this::getClienteByFuente).toList();

    //return apis.stream().flatMap(client -> client.getHechos().stream()).map(ManualHechoMapper::mapHechoToHechoOutputDTO).toList();

    return fuentesAsociadas.stream().flatMap(f -> this.hechosRepository.findHechosByFuente(f).stream()).collect(Collectors.toList());
  }


  @Override
  public void actualizarHechosProxyMetamapa() {
    this.hechosRepository.saveAll(this.proxyWebClient.getHechosMetamapa());
  }*/

  @Override
  public void aplicarAlgoritmoDeConsenso() {
    List<Hecho> hechos = this.hechosRepository.findAll();
    Integer cantDeFuentes = this.fuentesRepository.findAll().size();
    this.evaluadorDeConsenso.aplicarConsenso(hechos, cantDeFuentes);
  }

  @Override
  public List<HechoOutputDTO> getHechos() {
    List<Hecho> hechos = this.hechosRepository.findAll();
    return hechos.stream().map(this::HechoToHechoDTO).toList();
  }

  private HechoOutputDTO HechoToHechoDTO(Hecho hecho) {

    UbicacionDto ubicacionDto = UbicacionDto.builder()
        .latitud(hecho.getUbicacion().getLatitud())
        .longitud(hecho.getUbicacion().getLongitud())
        //.provincia(hecho.getProvincia())
        .build();
    return HechoOutputDTO.builder()
        .id(hecho.getId())
        .titulo(hecho.getTitulo())
        .descripcion(hecho.getDescripcion())
        .categoria(hecho.getCategoria().getNombre())
        .ubicacion(ubicacionDto)
        .fechaCreacion(hecho.getFechaCreacion())
        .fechaAcontecimiento(hecho.getFechaAcontecimiento())
        .etiquetas(hecho.getEtiquetas())
        .archivosMultimedia(Multimedia.toDTOList(hecho.getArchivosMultimedia()))
        .build();
  }

  @Override
  public void cargarProvinciasEnHechosSinUbicacion() {
    List<Hecho> hechosSinProvincia = this.hechosRepository.findHechosSinProvincia();

    if (hechosSinProvincia.isEmpty()) {
      log.info("✅ No hay hechos sin provincia para actualizar.");
      return;
    }

    log.info("🔄 Procesando {} hechos sin provincia...", hechosSinProvincia.size());

    hechosSinProvincia.forEach(this::cargarProvincia);

    this.hechosRepository.saveAll(hechosSinProvincia);

    log.info("✅ Provincias actualizadas correctamente.");
  }

  @Override
  public Hecho actualizarDatosHecho(Hecho hechoExistente, HechoResponseDTO dto) {
    hechoExistente.setTitulo(dto.getTitulo());
    hechoExistente.setDescripcion(dto.getDescripcion());

    Categoria categoria = this.categoriaService.buscarPorNombre(dto.getCategoria());
    hechoExistente.setCategoria(categoria);

    hechoExistente.setFechaAcontecimiento(dto.getFecha_hecho());
    hechoExistente.setEliminado(dto.getEliminado());

    List<Multimedia> multimedia = dto.getMultimedia() != null
        ? Multimedia.fromDTOList(dto.getMultimedia())
        : List.of();
    hechoExistente.setArchivosMultimedia(multimedia);

    hechoExistente.setEtiquetas(dto.getEtiquetas());

    hechoExistente.setUbicacion(new Ubicacion(dto.getLatitud(), dto.getLongitud()));

    return hechoExistente;
  }


  @Override
  public List<String> getProvinciasDeHechos() {
    return this.hechosRepository.findProvinciasDeHechos();
  }

  public void cargarProvincia(Hecho hecho) {
    try {
      Ubicacion ubicacion = hecho.getUbicacion();

      if (ubicacion == null || ubicacion.getLatitud() == null || ubicacion.getLongitud() == null) {
        throw new ValidationException("Ubicación inválida o incompleta para el hecho ID " + hecho.getId());
      }

      UbicacionGeoRefDTO provinciaObtenida = this.apiGeoRef.getProvincia(ubicacion.getLatitud(), ubicacion.getLongitud());

      if (provinciaObtenida == null ||
          provinciaObtenida.getUbicacion() == null ||
          provinciaObtenida.getUbicacion().getProvincia() == null ||
          provinciaObtenida.getUbicacion().getProvincia().getNombre() == null) {
        hecho.setProvincia("Desconocido");
        throw new GeoRefException("La API GeoRef no devolvió una provincia válida para el hecho ID " + hecho.getId());
      }

      hecho.setProvincia(provinciaObtenida.getUbicacion().getProvincia().getNombre());
    } catch (GeoRefException e) {
      // para no interrumpir el proceso completo
      log.warn("⚠️ No se pudo obtener la provincia para el hecho ID {}: {}", hecho.getId(), e.getMessage());
    } catch (Exception e) {
      log.error("❌ Error inesperado al procesar el hecho ID {}: {}", hecho.getId(), e.getMessage(), e);
    }

  }

}
