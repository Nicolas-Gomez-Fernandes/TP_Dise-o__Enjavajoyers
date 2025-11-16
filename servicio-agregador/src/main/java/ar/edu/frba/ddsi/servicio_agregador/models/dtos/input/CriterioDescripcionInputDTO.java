package ar.edu.frba.ddsi.servicio_agregador.models.dtos.input;

import lombok.Data;

@Data
public class CriterioDescripcionInputDTO extends CriterioPertenenciaInputDTO {
    private String descripcion;
}
