package ar.edu.frba.ddsi.servicio_agregador.models.dtos.input;

import lombok.Data;

@Data
public class CriterioTituloInputDTO extends CriterioPertenenciaInputDTO {
    private String titulo;
}