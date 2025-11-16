package ar.edu.frba.ddsi.servicio_estadistica.services.impl;

import ar.edu.frba.ddsi.servicio_estadistica.exceptions.estadisticas.EstadisticaException;
import ar.edu.frba.ddsi.servicio_estadistica.models.adapter.IAdapterAgregador;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.ResponseSpam;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output.EstadisticaCategoriaOutPutDTO;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output.EstadisticaColeccionOutputDTO;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output.EstadisticaSolicitudDeEliminacionSpamOutPutDTO;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output.EstadisticaHechosCategoriaOutPutDTO;
import ar.edu.frba.ddsi.servicio_estadistica.models.entities.EstadisticaCategoria;
import ar.edu.frba.ddsi.servicio_estadistica.models.entities.EstadisticaColeccion;
import ar.edu.frba.ddsi.servicio_estadistica.models.entities.EstadisticaSolicitudDeEliminacionSpam;
import ar.edu.frba.ddsi.servicio_estadistica.repositories.ICategoriaRepository;
import ar.edu.frba.ddsi.servicio_estadistica.repositories.IColeccionRepository;
import ar.edu.frba.ddsi.servicio_estadistica.repositories.ISolicitudSpamRepository;
import ar.edu.frba.ddsi.servicio_estadistica.services.IEstadisticasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.PageRequest;

@Slf4j
@Service
public class EstadisticasService implements IEstadisticasService {
  private IAdapterAgregador adapterAgregador;
  private ICategoriaRepository categoriaRepository;
  private IColeccionRepository coleccionRepository;
  private ISolicitudSpamRepository solicitudSpamRepository;

  public EstadisticasService(IAdapterAgregador adapterAgregador, ICategoriaRepository categoriaRepository, IColeccionRepository coleccionRepository, ISolicitudSpamRepository solicitudSpamRepository) {
    this.adapterAgregador = adapterAgregador;
    this.solicitudSpamRepository = solicitudSpamRepository;
    this.categoriaRepository = categoriaRepository;
    this.coleccionRepository = coleccionRepository;
  }

  @Override
  public void actualizarEstadisticas() {
    this.actualizarEstadisticasCategorias();
    this.actualizarEstadisticasColeccion();
    this.actualizarEstadisticasSpam();
  }

  @Override
  public List<EstadisticaColeccionOutputDTO> estadisticaColeccion(Integer size) {
    try {
      if(size == null) {
        return this.coleccionRepository.findAll().stream().map(this::fromColeccion).toList();// Valor por defecto si no se proporciona un tamaño válido
      }
      List<EstadisticaColeccion> estadisticas = this.coleccionRepository.findLimited(PageRequest.of(0, size));
      return estadisticas.stream().map(this::fromColeccion).toList();
    }
    catch (DataAccessException e) {
      log.error("Error de acceso a datos al consultar estadística de colección: {}", e.getMessage());
      throw new EstadisticaException("Error al acceder a la base de datos", e);
    } catch (Exception e) {
      log.error("Error inesperado al obtener la estadística de colección: {}", e.getMessage(), e);
      throw new EstadisticaException("Error interno al procesar la solicitud de estadística", e);
    }
    /*catch (DataAccessException e) {
      log.error("Error de acceso a datos al consultar estadística con ID {}: {}", id, e.getMessage());
      throw new EstadisticaException("Error al acceder a la base de datos", e);
    } catch (Exception e) {
      log.error("Error inesperado al obtener la estadística de colección con ID {}: {}", id, e.getMessage(), e);
      throw new EstadisticaException("Error interno al procesar la solicitud de estadística", e);
    }*/
  }


  @Override
  public EstadisticaSolicitudDeEliminacionSpamOutPutDTO estadisticaSolicitudesSpam() {
    return new EstadisticaSolicitudDeEliminacionSpamOutPutDTO();
  }


  @Override
  public List<EstadisticaCategoriaOutPutDTO> estadisticaCategoria(Integer size) {
    try {
      if(size == null) {
        return this.categoriaRepository.findAll().stream().map(this::fromCategoria).toList();// Valor por defecto si no se proporciona un tamaño válido
      }
      List<EstadisticaCategoria> estadisticas = this.categoriaRepository.findLimited(PageRequest.of(0, size));
      return estadisticas.stream().map(this::fromCategoria).toList();
    }
    catch (DataAccessException e) {
      log.error("Error de acceso a datos al consultar estadística de categoria: {}", e.getMessage());
      throw new EstadisticaException("Error al acceder a la base de datos", e);
    } catch (Exception e) {
      log.error("Error inesperado al obtener la estadística de categoria: {}", e.getMessage(), e);
      throw new EstadisticaException("Error interno al procesar la solicitud de estadística", e);
    }
    /*catch (DataAccessException e) {
      log.error("Error de acceso a datos al consultar estadística con ID {}: {}", id, e.getMessage());
      throw new EstadisticaException("Error al acceder a la base de datos", e);
    } catch (Exception e) {
      log.error("Error inesperado al obtener la estadística de colección con ID {}: {}", id, e.getMessage(), e);
      throw new EstadisticaException("Error interno al procesar la solicitud de estadística", e);
    }*/
  }


  @Override
  public EstadisticaHechosCategoriaOutPutDTO estadisticaCategoriaConMasHechos(){
      try {
        return categoriaRepository
            .findTopByOrderByCantidadHechosCategoriaDesc()
            .map(this::fromCategoriaHecho)
            .orElseThrow(() -> {
              log.error("No se encontró estadística de categoría con más hechos");
              return new EstadisticaException("No se encontró estadística de categoría con más hechos");
            });
      } catch (DataAccessException e) {
        log.error("Error de acceso a datos al consultar estadística de categoría con más hechos: {}", e.getMessage());
        throw new EstadisticaException("Error al acceder a la base de datos", e);
      } catch (Exception e) {
        log.error("Error inesperado al obtener la estadística de categoría con más hechos: {}", e.getMessage(), e);
        throw new EstadisticaException("Error interno al procesar la solicitud de estadística", e);
      }
    }


  private EstadisticaColeccionOutputDTO fromColeccion(EstadisticaColeccion estadisticaColeccion) {
    return new EstadisticaColeccionOutputDTO() {{
      setId_coleccion(estadisticaColeccion.getIdColeccion());
      setTitulo_coleccion(estadisticaColeccion.getTituloColeccion());
      setCantidad_hechos(estadisticaColeccion.getCantidadHechos());
      setProvincia(estadisticaColeccion.getProvincia());
    }};
  }

  private EstadisticaHechosCategoriaOutPutDTO fromCategoriaHecho(EstadisticaCategoria estadisticaCat) {
    return new EstadisticaHechosCategoriaOutPutDTO() {{
      setCategoria(estadisticaCat.getCategoria());
      setCantidadHechos(estadisticaCat.getCantidadHechosCategoria());
    }};
  }

  private EstadisticaCategoriaOutPutDTO fromCategoria(EstadisticaCategoria estadisticaCategoria) {
    return new EstadisticaCategoriaOutPutDTO() {{
      setCategoria(estadisticaCategoria.getCategoria());
      setProvincia(estadisticaCategoria.getProvincia());
      setCantidad_hechos_provincia(estadisticaCategoria.getCantidadHechosProvincia());
      setHora(estadisticaCategoria.getHora());
      setCantidad_hechos_hora(estadisticaCategoria.getCantidadHechosHora());
    }};
  }



  private EstadisticaSolicitudDeEliminacionSpamOutPutDTO fromSolicitudSpam(EstadisticaSolicitudDeEliminacionSpam estadisticaSpam) {
    return new EstadisticaSolicitudDeEliminacionSpamOutPutDTO() {{
      setCantDeSolicitudesTotal(estadisticaSpam.getCantidadDESpam());
      setCantDeSolicitudesEliminadas(estadisticaSpam.getCantidadSolicitudesEliminacion());
      setPorcentaje((float) (estadisticaSpam.getCantidadDESpam()/estadisticaSpam.getCantidadSolicitudesEliminacion()));
    }};
  }

  private void actualizarEstadisticasSpam(){
    try {
      EstadisticaSolicitudDeEliminacionSpam estadistica = this.estadisticaToEntity(this.adapterAgregador.cantidadDeSolicitudesSpam());
      //Actualizar los valores en la DB
      this.solicitudSpamRepository.actualizar(estadistica.getCantidadDESpam(), estadistica.getCantidadSolicitudesEliminacion());
    }
    catch (Exception e) {
      log.error("Error al actualizar las estadísticas de spam: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private EstadisticaSolicitudDeEliminacionSpam estadisticaToEntity(ResponseSpam estadistica) {
    return EstadisticaSolicitudDeEliminacionSpam.builder()
        .cantidadDESpam(estadistica.getCantDeSpam())
        .cantidadSolicitudesEliminacion(estadistica.getCantDeSolicitudesTotal())
        .build();
  }

  private void actualizarEstadisticasColeccion(){
    try {
      List<EstadisticaColeccion> estadisticas = this.adapterAgregador.topProvinciaConMasHechosDeColecciones()
          .stream()
          .map(resp -> {
            EstadisticaColeccion.builder()
                .idColeccion(resp.getIdColeccion())
                .tituloColeccion(resp.getTituloColeccion())
                .cantidadHechos(resp.getCantidadHechos())
                .provincia(resp.getProvincia())
                .build();

            EstadisticaColeccion existente = this.coleccionRepository.findByIdColeccion(resp.getIdColeccion());
            // Si existe, actualizar los campos necesarios
            if(existente != null) {
              log.info("Actualizando estadística existente para la colección: {}", resp.getTituloColeccion());
              existente.setCantidadHechos(resp.getCantidadHechos());
              existente.setProvincia(resp.getProvincia());
              return existente;
            }

            return EstadisticaColeccion.builder()
                .idColeccion(resp.getIdColeccion())
                .tituloColeccion(resp.getTituloColeccion())
                .cantidadHechos(resp.getCantidadHechos())
                .provincia(resp.getProvincia())
                .build();

          })
          .toList();

      this.coleccionRepository.saveAll(estadisticas);
      log.info("Estadísticas de colección actualizadas correctamente.");
    } catch (Exception e) {
      log.error("Error al actualizar las estadísticas de colección: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private void actualizarEstadisticasCategorias() {
    try {
      List<EstadisticaCategoria> estadisticas = this.adapterAgregador.estadisticaCategoria()
          .stream()
          .map(resp -> {

            // Buscar si ya existe una estadística para la categoría
            EstadisticaCategoria existente = this.categoriaRepository.findByCategoria(resp.getCategoria());
            // Si existe, actualizar los campos necesarios
            if(existente != null) {
              log.info("Actualizando estadística existente para la categoría: {}", resp.getCategoria());
              existente.setCantidadHechosCategoria(resp.getCantidad_hechos_categoria());
              existente.setCantidadHechosProvincia(resp.getCantidad_hechos_provincia());
              existente.setProvincia(resp.getProvincia());
              existente.setCantidadHechosHora(resp.getCantidad_hechos_hora());
              existente.setHora(resp.getHora());
              return existente;
            }

            return EstadisticaCategoria.builder()
                .cantidadHechosCategoria(resp.getCantidad_hechos_categoria())
                .categoria(resp.getCategoria())
                .provincia(resp.getProvincia())
                .cantidadHechosProvincia(resp.getCantidad_hechos_provincia())
                .hora(resp.getHora())
                .cantidadHechosHora(resp.getCantidad_hechos_hora())
                .build();
          })
          .toList();

      this.categoriaRepository.saveAll(estadisticas);
      log.info("Estadísticas de categorías actualizadas correctamente.");

    } catch (Exception e) {
      log.error("Error al actualizar las estadísticas de categorías: {}", e.getMessage());
      throw new RuntimeException(e);
    }

  }
}
