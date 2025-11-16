package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.solicitudEliminacion;

import org.springframework.stereotype.Component;

@Component
public interface DetectorDeSpam {
  boolean esSpam(String texto);
}
