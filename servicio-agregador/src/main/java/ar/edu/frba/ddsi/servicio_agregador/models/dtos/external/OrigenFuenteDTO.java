package ar.edu.frba.ddsi.servicio_agregador.models.dtos.external;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.Fuente;
import lombok.Data;

@Data
public class OrigenFuenteDTO {
  private String fuente;
  private Fuente tipoFuente;
}
