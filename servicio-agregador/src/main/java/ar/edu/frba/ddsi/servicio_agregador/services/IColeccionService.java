package ar.edu.frba.ddsi.servicio_agregador.services;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.input.ColeccionInputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.ColeccionOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.HechoOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.paginacion.PageOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.TipoAlgoritmoDeConsenso;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.ICriterioPertenencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface IColeccionService {
  List<ColeccionOutputDTO> buscarTodas();

  PageOutputDTO<ColeccionOutputDTO> buscarPaginadoPersonalizado(Pageable pageable);

  Page<ColeccionOutputDTO> buscarPaginado(Pageable pageable);

  ColeccionOutputDTO buscarPorId(Long id);

  ColeccionOutputDTO crearColeccion(ColeccionInputDTO coleccion);

  void addCriterioDePertenencia(Long idColeccion, ICriterioPertenencia criterio);

  void refrescarColecciones();

  ColeccionOutputDTO update(Long id, ColeccionInputDTO coleccion);

  ColeccionOutputDTO agregarFuente(Long id, Long idFuente);

  void desasociarFuente(Long id, Long idFuente);

  void deleteColeccion(Long id);

  void updateAlgoritmoDeConsenso(Long id, TipoAlgoritmoDeConsenso Tipoalgoritmo);

  List<ColeccionOutputDTO> obtenerColeccionesDestacadas();

  List<HechoOutputDTO> obtenerHechosFiltrados(Long id, Boolean navegacionCurada, LocalDateTime fechaDesde, LocalDateTime fechaHasta, String provincia, Long categoriaId, Long fuenteId);
}
