package ar.edu.frba.ddsi.servicio_agregador.models.dtos.output;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.external.dinamica.MultimediaResponseDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Ubicacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HechoOutputDTO {
  private Long id;
  private String titulo;
  private String descripcion;
  private String categoria;
  private UbicacionDto ubicacion;
  private LocalDate fecha;
  private LocalDateTime fechaCreacion;
  private LocalDateTime fechaAcontecimiento;
  private List<MultimediaResponseDTO> archivosMultimedia;
  private List<String> etiquetas;
}
