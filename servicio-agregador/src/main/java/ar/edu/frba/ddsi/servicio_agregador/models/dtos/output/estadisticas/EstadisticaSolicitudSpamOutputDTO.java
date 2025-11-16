package ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.estadisticas;

import lombok.Data;

@Data
public class EstadisticaSolicitudSpamOutputDTO {
    private Integer cantidadDeSpam;
    private Integer cantidadSolicitudesEliminacion;
}
