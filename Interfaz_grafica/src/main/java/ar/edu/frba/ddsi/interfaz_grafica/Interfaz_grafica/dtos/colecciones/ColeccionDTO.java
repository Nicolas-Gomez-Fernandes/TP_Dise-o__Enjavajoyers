package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.colecciones;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColeccionDTO {
  private Long id;
  private String titulo;
  private String descripcion;
  private Integer cantHechos;
  private String imagen_url;
}
