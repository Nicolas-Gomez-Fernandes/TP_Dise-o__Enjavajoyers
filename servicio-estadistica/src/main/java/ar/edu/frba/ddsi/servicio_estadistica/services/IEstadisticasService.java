package ar.edu.frba.ddsi.servicio_estadistica.services;

import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output.EstadisticaCategoriaOutPutDTO;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output.EstadisticaColeccionOutputDTO;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output.EstadisticaSolicitudDeEliminacionSpamOutPutDTO;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output.EstadisticaHechosCategoriaOutPutDTO;
import ar.edu.frba.ddsi.servicio_estadistica.models.entities.EstadisticaCategoria;
import java.util.List;

public interface IEstadisticasService {
  void actualizarEstadisticas();
  List<EstadisticaColeccionOutputDTO> estadisticaColeccion(Integer size);

  EstadisticaSolicitudDeEliminacionSpamOutPutDTO estadisticaSolicitudesSpam();

  List<EstadisticaCategoriaOutPutDTO> estadisticaCategoria(Integer size);

  EstadisticaHechosCategoriaOutPutDTO estadisticaCategoriaConMasHechos();
}
