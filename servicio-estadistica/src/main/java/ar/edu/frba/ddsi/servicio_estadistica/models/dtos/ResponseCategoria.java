package ar.edu.frba.ddsi.servicio_estadistica.models.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResponseCategoria {
    private String categoria;
    private Long cantidad_hechos_categoria;

    private String provincia;
    private Long cantidad_hechos_provincia;

    private Integer hora;
    private Long cantidad_hechos_hora;
}
