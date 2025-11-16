package ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.estadisticas;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EstadisticaCategoriaOutputDTO {
    private String categoria;
    private Integer cantidad_hechos_categoria;

    private String provincia;
    private Integer cantidad_hechos_provincia;

    private Integer hora;
    private Integer cantidad_hechos_hora;
}
