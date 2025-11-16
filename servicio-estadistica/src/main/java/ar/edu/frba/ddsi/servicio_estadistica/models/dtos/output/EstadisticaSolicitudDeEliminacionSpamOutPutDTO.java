package ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output;


import lombok.Data;

@Data
public class EstadisticaSolicitudDeEliminacionSpamOutPutDTO {
    private Long cantDeSolicitudesTotal;
    private Long cantDeSolicitudesEliminadas;
    private Float porcentaje;
}
