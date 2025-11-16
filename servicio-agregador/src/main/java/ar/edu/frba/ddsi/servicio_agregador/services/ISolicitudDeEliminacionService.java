package ar.edu.frba.ddsi.servicio_agregador.services;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.input.SolicitudEliminacionInputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.SolicitudDeEliminacionOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.SolicitudEliminacion;
import java.util.List;

public interface ISolicitudDeEliminacionService {
  SolicitudDeEliminacionOutputDTO crearSolicitud(SolicitudEliminacionInputDTO solicitudDeEliminacion);
  List<SolicitudDeEliminacionOutputDTO> buscarTodos();
  void actualizarEstadoDeSolicitud(Long id, boolean aceptado);
}
