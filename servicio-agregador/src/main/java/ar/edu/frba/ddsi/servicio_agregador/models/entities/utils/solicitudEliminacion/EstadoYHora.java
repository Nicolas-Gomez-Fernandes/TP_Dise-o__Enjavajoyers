package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.solicitudEliminacion;


import ar.edu.frba.ddsi.servicio_agregador.models.entities.Estado;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "historial_solicitudes_eliminacion")
public class EstadoYHora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @Getter
    @Column(name = "fecha_cambio_estado", nullable = false, columnDefinition = "TIMESTAMP" )
    private LocalDateTime fecha;

    @OneToOne(fetch = FetchType.LAZY)
    private Usuario usuarioQueRealizoElCambio;

    public EstadoYHora(Estado estado, LocalDateTime fecha) {
        this.estado = estado;
        this.fecha = fecha;
        this.usuarioQueRealizoElCambio = null;
    }
}
