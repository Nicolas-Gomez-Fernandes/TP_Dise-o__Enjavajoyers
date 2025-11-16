package ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output;

import lombok.Data;

@Data
public class EstadisticaColeccionOutputDTO {
  private Long id_coleccion;
  private String titulo_coleccion;
  private Long cantidad_hechos;
  private String provincia;
}
