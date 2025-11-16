package ar.edu.frba.ddsi.servicio_agregador.models.dtos.input;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import lombok.Data;

@Data
public class CriterioCategoriaInputDTO extends CriterioPertenenciaInputDTO {
    private String categoria;
}