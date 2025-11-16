package ar.edu.frba.ddsi.servicio_agregador.models.dtos.external.dinamica;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MultimediaResponseDTO {
  private String rutaArchivo;
  private String tipoArchivo;
}
