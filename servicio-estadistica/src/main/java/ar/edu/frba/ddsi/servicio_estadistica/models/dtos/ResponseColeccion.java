package ar.edu.frba.ddsi.servicio_estadistica.models.dtos;

import lombok.Data;

@Data
public class ResponseColeccion {
    private Long idColeccion;
    private String tituloColeccion;
    private Long cantidadHechos;
    private String provincia;
}
