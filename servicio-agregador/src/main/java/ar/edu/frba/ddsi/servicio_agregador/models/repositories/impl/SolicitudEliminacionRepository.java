package ar.edu.frba.ddsi.servicio_agregador.models.repositories.impl;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.SolicitudEliminacion;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.ISolicitudEliminacionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public class SolicitudEliminacionRepository {
  private List<SolicitudEliminacion> solicitudes;

  public void guardar(SolicitudEliminacion solicitudEliminacion){
    this.solicitudes.add(solicitudEliminacion);
  }


  public SolicitudEliminacion buscarPorId(Long id){
    return this.solicitudes.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
  }
}
