package ar.edu.frba.ddsi.servicio_agregador.models.dtos.output;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SolicitudDeEliminacionOutputDTO {
    private Long id;
    private Long id_hecho;
    private String fundamento;
    private Estado estado;
    private LocalDateTime fechaCreacion;
}
