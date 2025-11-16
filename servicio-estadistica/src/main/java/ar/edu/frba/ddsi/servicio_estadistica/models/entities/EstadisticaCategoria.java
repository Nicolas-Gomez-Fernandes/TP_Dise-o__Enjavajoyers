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
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "estadisticas_categoria")
public class EstadisticaCategoria{
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "categoria", nullable = false)
    private String categoria;
    @Column(name = "provincia")
    private String provincia;
    @Column(name = "cantidad_hechos_provincia")
    private Long cantidadHechosProvincia;
    @Column(name = "hora")
    private Integer hora;
    @Column(name = "cantidad_hechos_hora")
    private Long cantidadHechosHora;
    @Column(name="cantidad_hechos_categoria")
    private Long cantidadHechosCategoria;

}
