package ar.edu.utn.frba.ddsi.metamapa.models.dtos.output;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class HechoOutputDTO {
  private Long id;
  private String titulo;
  private String descripcion;
  private String categoria;
  private Double latitud;
  private Double longitud;
  private LocalDateTime fecha_hecho;
  private Boolean eliminado;
  private OrigenFuenteDTO origen;
}
