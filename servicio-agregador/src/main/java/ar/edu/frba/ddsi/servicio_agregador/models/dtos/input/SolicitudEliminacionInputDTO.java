package ar.edu.frba.ddsi.servicio_agregador.models.dtos.input;

import lombok.Data;

@Data
public class SolicitudEliminacionInputDTO {
  private Long id_hecho;
  private String fundamento;
}
