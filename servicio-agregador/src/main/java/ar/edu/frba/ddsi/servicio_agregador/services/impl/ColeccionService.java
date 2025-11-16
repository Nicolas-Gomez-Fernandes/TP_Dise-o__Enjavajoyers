package ar.edu.frba.ddsi.servicio_agregador.services.impl;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.input.ColeccionInputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.mapper.ManualColeccionMapper;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.mapper.ManualHechoMapper;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.ColeccionOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.HechoOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.paginacion.PageOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Coleccion;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import ar.edu.frba.ddsi.servicio_agregador.models.paginacion.PageMapper;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.fuentes.OrigenFuente;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.TipoAlgoritmoDeConsenso;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.ICriterioPertenencia;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.IColeccionesRepository;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.IFuentesRepository;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.IHechosRepository;
import ar.edu.frba.ddsi.servicio_agregador.services.IColeccionService;
import ar.edu.frba.ddsi.servicio_agregador.services.ICriteriosPertenenciaService;
import ar.edu.frba.ddsi.servicio_agregador.services.IHechosService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColeccionService implements IColeccionService {
  private ICriteriosPertenenciaService criteriosPertenciaService;
  private IHechosRepository hechosRepository;
  private IHechosService hechosService;
  private IFuentesRepository fuentesRepository;
  private IColeccionesRepository coleccionesRepository;
  private ManualColeccionMapper coleccionMapper;
  private PageMapper pageMapper;

  public ColeccionService(ICriteriosPertenenciaService criteriosPertenciaService, IHechosRepository hechosRepository, IHechosService hechosService, IFuentesRepository fuentesRepository, IColeccionesRepository coleccionesRepository, @Value("${base-url}") String baseUrl) {
    this.criteriosPertenciaService = criteriosPertenciaService;
    this.hechosRepository = hechosRepository;
    this.hechosService = hechosService;
    this.fuentesRepository = fuentesRepository;
    this.coleccionesRepository = coleccionesRepository;
    this.coleccionMapper = new ManualColeccionMapper();
    this.pageMapper = new PageMapper( baseUrl+ "/agregador/colecciones");
  }

  @Override
  public List<ColeccionOutputDTO> buscarTodas() {
    return this.coleccionesRepository
        .findAll()
        .stream()
        .map(coleccionMapper::mapToDTO)
        .toList();
  }

  @Override
  public PageOutputDTO<ColeccionOutputDTO> buscarPaginadoPersonalizado(Pageable pageable) {
    Page<Coleccion> page = this.coleccionesRepository.findAll(pageable);
    return this.pageMapper.toPageOutputDTO(page.map(coleccionMapper::mapToDTO));
    /*return this.coleccionesRepository.findAll(pageable)
        .map(ColeccionMapper.INSTANCE::coleccionToColeccionOutputDTO);*/
  }

  @Override
  public Page<ColeccionOutputDTO> buscarPaginado(Pageable pageable) {
    return this.coleccionesRepository.findAll(pageable)
        .map(coleccionMapper::mapToDTO);
  }

  @Override
  public ColeccionOutputDTO buscarPorId(Long id) {
    var coleccion = this.coleccionesRepository.findById(id).orElseThrow(() -> new RuntimeException("Coleccion no encontrada"));
    if (coleccion == null) {
      return null;
    }
    return coleccionMapper.mapToDTO(coleccion);
  }

  // TODO -- cambiar el input para que lleguen los ids de lls criterios O crearlos con el service desde aca
  @Override
  public ColeccionOutputDTO crearColeccion(ColeccionInputDTO coleccionInputDTO) {
    // validar que el usuario que cree una coleccion posea los permisos para hacerlo. solo el administrador puede crear colecciones
    /*Permiso crearColeccion = new Permiso();
    if (!coleccionInputDTO.getCreador().getRol().tenesPermiso(crearColeccion)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"El usuario " + coleccionInputDTO.getCreador().getNombre() + " " + coleccionInputDTO.getCreador().getApellido() + " no tiene permiso de crear el coleccion");
    }*/

    // Validación de datos de entrada
    if (coleccionInputDTO.getTitulo() == null || coleccionInputDTO.getTitulo().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El título de la colección no puede estar vacío");
    }


    // Creamos la coleccion
    Coleccion coleccion = new Coleccion(coleccionInputDTO.getTitulo(), coleccionInputDTO.getDescripcion());


    // Traemos las fuentes seleccionadas
    List<OrigenFuente> fuentesAsociadas = coleccionInputDTO.getFuentesAsociadas().stream()
        .map(f ->
            this.fuentesRepository.findById(f)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Fuente no encontrada con ID: " + f)))
        .toList();


    // Traemos los hechos
    List<Hecho> hechos = fuentesAsociadas.stream()
        .flatMap(f -> this.hechosRepository.findHechosByFuente(f).stream()).toList();

    coleccion.agregarHechos(hechos);

    // Agregamos los criterios
    List<ICriterioPertenencia> criterios = null;
    if (coleccionInputDTO.getCriterios() != null) {
      criterios = coleccionInputDTO.getCriterios()
                  .stream()
                  .map(this.criteriosPertenciaService::crearCriterio)
                  .toList();

      criterios.forEach(coleccion::agregarCriterioDePertenencia); // se aplican los criterios al agregarlos
    }

    // Asociamos las fuentes a la coleccion
    coleccion.asociarFuentes(fuentesAsociadas);


    // agregamos el algoritmo de consenso
      coleccion.setAlgoritmo(coleccionInputDTO.getAlgoritmoDeConsenso());

    // Guardamos la coleccion en el repo
    this.coleccionesRepository.save(coleccion);

    return coleccionMapper.mapToDTO(coleccion);
  }

  @Override
  public void addCriterioDePertenencia(Long idColeccion, ICriterioPertenencia criterio) {
    // Busco la coleccion
    Coleccion coleccion = this.coleccionesRepository.findById(idColeccion)
        .orElseThrow(() -> new RuntimeException("Coleccion no encontrada: " + idColeccion));

    // Aplico criterio para que quede solo con los hechos que cumplen
    coleccion.agregarCriterioDePertenencia(criterio);

    // Guardo en el repositorio
    this.coleccionesRepository.save(coleccion);
  }


  @Override
  public void refrescarColecciones() {
    this.hechosService.actualizarYCargarNuevosHechos();
    this.coleccionesRepository.findAll().forEach(this::actualizarHechosColeccion);
  }

  @Override
  public ColeccionOutputDTO update(Long id, ColeccionInputDTO input) { // TODO
    Coleccion coleccion = this.coleccionesRepository.findById(id)
        .orElseThrow(() ->  new RuntimeException("Coleccion no encontrada: " + id));

    coleccion.setTitulo(input.getTitulo());
    coleccion.setDescripcion(input.getDescripcion());
    //coleccion.setFuentes(input.getFuentesAsociadas());
    //input.getFuentesAsociadas().forEach(f -> fuentesRepository.save(f));

    return coleccionMapper.mapToDTO(coleccion);
  }

  @Override
  public ColeccionOutputDTO agregarFuente(Long id, Long idFuente) {
    Coleccion coleccion = this.coleccionesRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Colección no encontrada con ID: " + id));

    OrigenFuente origenFuente  = fuentesRepository.findById(idFuente)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fuente no encontrada"));

    coleccion.asociarFuente(origenFuente);
    this.actualizarHechosColeccion(coleccion);

    return coleccionMapper.mapToDTO(coleccion);
  }

  // TODO revisar si esta bien o se puede mejorar
  @Override
  public void deleteColeccion(Long id) {
    this.coleccionesRepository.deleteById(id);
  }

  private void actualizarHechosColeccion(Coleccion c) {
    List<Hecho> hechosNuevos = hechosService.getHechosByFuentes(c.getFuentes());
    c.agregarHechos(hechosNuevos);
    c.aplicarCriteriosDePertenencia();
  }

  @Override
  public void updateAlgoritmoDeConsenso(Long id, TipoAlgoritmoDeConsenso tipoalgoritmo) {
    Coleccion coleccion = this.coleccionesRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Colección no encontrada con ID: " + id));

    coleccion.modificarAlgoritmoDeConsenso(tipoalgoritmo);
  }

  @Override
  public void desasociarFuente(Long id, Long idFuente) {
    Coleccion coleccion = this.coleccionesRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Colección no encontrada con ID: " + id));

    OrigenFuente origenFuente  = fuentesRepository.findById(idFuente)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fuente no encontrada"));

    coleccion.desAsociarFuente(origenFuente);

    this.actualizarHechosColeccion(coleccion);
  }

  @Override
  public List<ColeccionOutputDTO> obtenerColeccionesDestacadas() {
    // TODO definir qué hace que una colección sea destacada
    return this.coleccionesRepository.findTop5ByOrderByIdDesc()
        .stream()
        .map(coleccionMapper::mapToDTO)
        .toList();
  }


  @Override
  public List<HechoOutputDTO> obtenerHechosFiltrados(Long id, Boolean navegacionCurada,
                                                     LocalDateTime fechaDesde, LocalDateTime fechaHasta,
                                                     String provincia, Long categoriaId, Long fuenteId) {
    Coleccion coleccion = coleccionesRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Colección no encontrada con ID: " + id));

    // obtener IDs de hechos según navegación curada o no
    List<Long> idsHechos = coleccion.getHechos(navegacionCurada).stream()
        .map(Hecho::getId)
        .collect(Collectors.toList());

    if (idsHechos.isEmpty()) {
      return List.of();
    }

    // construir especificación dinámica
    Specification<Hecho> spec = (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      // filtrar por IDs de hechos de la colección
      predicates.add(root.get("id").in(idsHechos));

      // filtrar por fecha desde
      if (fechaDesde != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get("fechaAcontecimiento"), fechaDesde));
      }

      // filtrar por fecha hasta
      if (fechaHasta != null) {
        predicates.add(cb.lessThanOrEqualTo(root.get("fechaAcontecimiento"), fechaHasta));
      }

      // filtrar por provincia
      if (provincia != null && !provincia.isBlank()) {
        predicates.add(cb.equal(cb.lower(root.get("provincia")), provincia.toLowerCase()));
      }

      // filtrar por categoría
      if (categoriaId != null) {
        predicates.add(cb.equal(root.get("categoria").get("id"), categoriaId));
      }

      // filtrar por fuente
      if (fuenteId != null) {
        predicates.add(cb.equal(root.get("fuente").get("id"), fuenteId));
      }

      predicates.add(cb.isFalse(root.get("eliminado")));

      return cb.and(predicates.toArray(new Predicate[0]));
    };

    // ejecutar query con especificación
    return hechosRepository.findAll(spec).stream()
        .map(ManualHechoMapper::mapHechoToHechoOutputDTO)
        .collect(Collectors.toList());
  }


//  @Override
//  public List<HechoOutputDTO> obtenerHechos(Long id, boolean curado) {
//    Coleccion coleccion = this.coleccionesRepository.findById(id)
//        .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleccion no encontrada con ID: " + id));
//
//    return coleccion.getHechos(curado).stream().map(ManualHechoMapper::mapHechoToHechoOutputDTO).collect(Collectors.toList());
//  }
}
