package ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output;

import lombok.Data;

@Data
public class EstadisticaHechosCategoriaOutPutDTO {
    private String categoria;
    private Long cantidadHechos;
}
