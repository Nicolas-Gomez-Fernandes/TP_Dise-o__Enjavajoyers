package ar.edu.utn.frba.ddsi.metamapa.services.impl;


import ar.edu.utn.frba.ddsi.metamapa.models.dtos.input.HechoInputCsvDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.dtos.mapper.ManualHechoMapper;
import ar.edu.utn.frba.ddsi.metamapa.models.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.adapters.IClimaAdapter;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.importador.ImportadorDeHechos;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Categoria;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Ubicacion;
import ar.edu.utn.frba.ddsi.metamapa.models.repositories.IHechosRepository;
import ar.edu.utn.frba.ddsi.metamapa.services.ICategoriaService;
import ar.edu.utn.frba.ddsi.metamapa.services.IFuentesService;
import ar.edu.utn.frba.ddsi.metamapa.services.IHechosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class HechosService implements IHechosService {
  private static final Logger logger = LoggerFactory.getLogger(HechosService.class);

  private ICategoriaService categoriaService;
  private IFuentesService fuentesService;
  private IHechosRepository hechosRepository;
  private ImportadorDeHechos importador;
  private IClimaAdapter climaAdapter;

  public HechosService(IHechosRepository hechosRepository, IFuentesService fuentesService, ICategoriaService categoriaService, IClimaAdapter climaAdapter) {
    this.fuentesService = fuentesService;
    this.hechosRepository = hechosRepository;
    this.importador = new ImportadorDeHechos();
    this.categoriaService = categoriaService;
    this.climaAdapter = climaAdapter;
  }
/*
  @Override
  public void cargarDesdeDataset(String path) {
    importador.importar(path)
        .doOnNext(hechos -> {
          logger.info("💾 Guardando {} hechos en el repositorio...", hechos.size());
          hechosRepository.saveAll(hechos);
          logger.info("✅ Guardados {} hechos correctamente", hechos.size());
        })
        .doOnError(error -> logger.error("❌ Error al cargar dataset desde {}: {}", path, error.getMessage(), error))
        .subscribeOn(Schedulers.boundedElastic())
        .subscribe();
  }
*/


  @Override
  public void eliminar(Long id) {
    Hecho hecho = this.hechosRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hecho no encontrado con id: " + id));
    hecho.setEliminado(true);
    this.hechosRepository.save(hecho);
  }

  @Override
  public HechoOutputDTO buscarPorId(Long id) {
    var hecho = this.hechosRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Hecho no encontrado con id: " + id));

    return ManualHechoMapper.map(hecho);
  }


  //Instancia un hecho y las categorias asociadas
  public Hecho crearHecho(HechoInputCsvDTO h) {
    Categoria categoria = this.categoriaService.buscarOcrearCategoria(h.getCategoria());
    Ubicacion ubicacion = new Ubicacion();
    ubicacion.setLatitud(h.getLatitud());
    ubicacion.setLongitud(h.getLongitud());

    Hecho hecho = new Hecho();
    hecho.setTitulo(h.getTitulo());
    hecho.setDescripcion(h.getDescripcion());
    hecho.setFecha(h.getFecha());
    hecho.setCategoria(categoria);
    hecho.setUbicacion(ubicacion);
    hecho.setFechaCreacion(LocalDateTime.now());
    hecho.setEliminado(false);
    return hecho;
  }

  // Elimina los hechos duplicados por titulo
  public List<Hecho> eliminarDuplicados(List<Hecho> hechos) {
    Map<String, Hecho> mapHechos = new HashMap<>();
    for (Hecho hecho : hechos) {
      if (!mapHechos.containsKey(hecho.getTitulo())) {
        mapHechos.put(hecho.getTitulo(), hecho);
      } else {
        // Si ya existe, se pisan los atributos del existente
        Hecho existente = mapHechos.get(hecho.getTitulo());
        existente.setDescripcion(hecho.getDescripcion());
        existente.setCategoria(hecho.getCategoria());
        existente.setFecha(hecho.getFecha());
        existente.setUbicacion(hecho.getUbicacion());
        //existente.setEtiquetas(hecho.getEtiquetas()); //TODO - ver como manejar las etiquetas
        existente.setFuente(hecho.getFuente());
        existente.setEliminado(hecho.getEliminado());
      }
    }
    return mapHechos.values().stream().toList();
  }

  @Async
  public void guardarHechos(List<Hecho> hechos) {
    log.info("💾 Guardando {} hechos en el repositorio...", hechos.size());

    int nuevos = 0;
    int actualizados = 0;

    // Implementar UPSERT: actualizar si existe, crear si no existe
    for (Hecho hecho : hechos) {
      java.util.Optional<Hecho> existente = hechosRepository.findByTitulo(hecho.getTitulo());
      
      if (existente.isPresent()) {
        // Si existe, actualizar sus atributos
        Hecho hechoExistente = existente.get();
        hechoExistente.setDescripcion(hecho.getDescripcion());
        hechoExistente.setCategoria(hecho.getCategoria());
        hechoExistente.setFecha(hecho.getFecha());
        hechoExistente.setUbicacion(hecho.getUbicacion());
        hechoExistente.setFuente(hecho.getFuente());
        hechoExistente.setEliminado(hecho.getEliminado());
        hechosRepository.save(hechoExistente);
        actualizados++;
      } else {
        // Si no existe, guardarlo como nuevo
        hechosRepository.save(hecho);
        nuevos++;
      }
    }

    log.info("✅ Guardados {} hechos nuevos y actualizados {} existentes", nuevos, actualizados);
    log.info("📊 Total de hechos en el repositorio: {}", this.hechosRepository.findAll().size());
  }

  @Override
  public List<HechoOutputDTO> getHechos() {
    return hechosRepository
        .findAll()
        .stream()
        .map(ManualHechoMapper::map)
        .toList();
  }

  @Override
  public List<HechoOutputDTO> getHechosFiltrados(String categoria, String titulo, String fechaDesde, String fechaHasta) {
    List<Hecho> hechos = hechosRepository.findAll();
    
    // Filtrar por categoría
    if (categoria != null && !categoria.trim().isEmpty()) {
      String categoriaLower = categoria.toLowerCase();
      hechos = hechos.stream()
          .filter(h -> h.getCategoria() != null && 
                       h.getCategoria().getNombre().toLowerCase().contains(categoriaLower))
          .toList();
    }
    
    // Filtrar por título
    if (titulo != null && !titulo.trim().isEmpty()) {
      String tituloLower = titulo.toLowerCase();
      hechos = hechos.stream()
          .filter(h -> h.getTitulo() != null && 
                       h.getTitulo().toLowerCase().contains(tituloLower))
          .toList();
    }
    
    // Filtrar por rango de fechas (convertir LocalDateTime a LocalDate para comparación)
    if (fechaDesde != null && !fechaDesde.trim().isEmpty()) {
      try {
        LocalDate desde = LocalDate.parse(fechaDesde);
        hechos = hechos.stream()
            .filter(h -> h.getFecha() != null && !h.getFecha().toLocalDate().isBefore(desde))
            .toList();
      } catch (Exception e) {
        log.warn("⚠️ Formato de fecha inválido para fechaDesde: {}", fechaDesde);
      }
    }
    
    if (fechaHasta != null && !fechaHasta.trim().isEmpty()) {
      try {
        LocalDate hasta = LocalDate.parse(fechaHasta);
        hechos = hechos.stream()
            .filter(h -> h.getFecha() != null && !h.getFecha().toLocalDate().isAfter(hasta))
            .toList();
      } catch (Exception e) {
        log.warn("⚠️ Formato de fecha inválido para fechaHasta: {}", fechaHasta);
      }
    }
    
    return hechos.stream()
        .map(ManualHechoMapper::map)
        .toList();
  }


}
