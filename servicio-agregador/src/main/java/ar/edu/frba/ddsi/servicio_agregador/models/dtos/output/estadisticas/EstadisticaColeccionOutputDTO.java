package ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.estadisticas;

import lombok.Data;
import java.util.UUID;

@Data
public class EstadisticaColeccionOutputDTO {
    private Long idColeccion;
    private String tituloColeccion;
    private Integer cantidadHechos;
    private String provincia;
}
