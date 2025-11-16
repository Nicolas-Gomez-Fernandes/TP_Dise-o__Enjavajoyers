package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FiltroHecho {
  private String categoria;
  private LocalDateTime fecha_reporte_desde;
  private LocalDateTime fecha_reporte_hasta;
  private LocalDateTime fecha_acontecimiento_desde;
  private LocalDateTime fecha_acontecimiento_hasta;
  private Ubicacion ubicacion;


}