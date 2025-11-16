package ar.edu.frba.ddsi.servicio_agregador.models.dtos.input;

import lombok.Data;
import lombok.Getter;

@Data
public class CriterioUbicacionInputDTO extends CriterioPertenenciaInputDTO{
    private Double latitud;
    private Double longitud;
}
