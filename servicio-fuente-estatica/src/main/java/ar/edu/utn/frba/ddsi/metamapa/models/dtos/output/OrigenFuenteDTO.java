package ar.edu.utn.frba.ddsi.metamapa.models.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrigenFuenteDTO {
  private String fuente;
  private String tipoFuente;
}
