package ar.edu.utn.frba.ddsi.metamapa.deprecado.models.solicitudEliminacion;


import ar.edu.utn.frba.ddsi.metamapa.deprecado.models.Estado;
import lombok.Getter;

import java.time.LocalDateTime;

public class EstadoYHora {
    @Getter
    public Estado estado;
    @Getter
    public LocalDateTime fecha;

    public EstadoYHora(Estado estado, LocalDateTime fecha) {
        this.estado = estado;
        this.fecha = fecha;
    }

}
