package ar.edu.frba.ddsi.servicio_agregador.models.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UbicacionDto {
  private Double latitud;
  private Double longitud;
  private String provincia;
}
