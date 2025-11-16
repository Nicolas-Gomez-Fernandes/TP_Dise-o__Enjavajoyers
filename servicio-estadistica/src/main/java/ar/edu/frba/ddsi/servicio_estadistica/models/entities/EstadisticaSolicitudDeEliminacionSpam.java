package ar.edu.frba.ddsi.servicio_estadistica.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "estadisticas_solicitudes_de_eliminacion_spam")
public class EstadisticaSolicitudDeEliminacionSpam {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cantidad_de_spam", nullable = false)
    private Long cantidadDESpam;
    @Column(name = "cantidad_de_solicitudes_de_eliminacion", nullable = false)
    private Long cantidadSolicitudesEliminacion;
}