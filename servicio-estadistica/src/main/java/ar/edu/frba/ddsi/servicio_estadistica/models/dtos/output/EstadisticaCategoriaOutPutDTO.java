package ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output;

import lombok.Data;

@Data
public class EstadisticaCategoriaOutPutDTO {
    private String categoria;
    private String provincia;
    private Long cantidad_hechos_provincia;
    private Integer hora;
    private Long cantidad_hechos_hora;
}
