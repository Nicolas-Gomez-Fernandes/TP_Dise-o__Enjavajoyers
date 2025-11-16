package ar.edu.frba.ddsi.servicio_estadistica.models.dtos;

import lombok.Data;

@Data
public class ResponseSpam {
    private Long cantDeSpam;
    private Long cantDeSolicitudesTotal;
}
