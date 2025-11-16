package ar.edu.frba.ddsi.servicio_agregador.models.dtos.input;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.TipoCriterio;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class CriterioPertenenciaInputDTO {
  private TipoCriterio tipo;
  private Long idCategoria;
  private String titulo;
  private String descripcion;
  private LocalDateTime desde;
  private LocalDateTime hasta;
  private Double latitud;
  private Double longitud;
}