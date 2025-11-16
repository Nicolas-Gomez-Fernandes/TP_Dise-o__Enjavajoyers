package ar.edu.frba.ddsi.servicio_agregador.models.dtos.input;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CriterioFechaInputDTO extends CriterioPertenenciaInputDTO {
    private LocalDateTime desde;
    private LocalDateTime hasta;
}