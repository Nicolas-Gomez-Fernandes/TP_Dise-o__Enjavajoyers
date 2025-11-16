package ar.edu.utn.frba.ddsi.metamapa.models.dtos.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RtaCargaHechoOutputDTO {
  private String mensaje;
  private int cantidadRegistros;
  private boolean exito;
}
