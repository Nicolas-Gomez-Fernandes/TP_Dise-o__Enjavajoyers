package ar.edu.frba.ddsi.servicio_agregador.models.dtos.external;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.external.dinamica.MultimediaResponseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoResponseDTO {
  private Long id;
  private String titulo;
  private String descripcion;
  private String categoria;
  private Double latitud;
  private Double longitud;
  private List<MultimediaResponseDTO> multimedia;
  private List<String> etiquetas;
  private LocalDateTime fecha_hecho;
  private Boolean eliminado;
  private OrigenFuenteDTO origen;
}
