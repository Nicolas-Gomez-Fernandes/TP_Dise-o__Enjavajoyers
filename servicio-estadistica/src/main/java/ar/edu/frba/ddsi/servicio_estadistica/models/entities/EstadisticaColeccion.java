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

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "estadisticas_colecciones")
public class EstadisticaColeccion {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_coleccion", nullable = false)
    private Long idColeccion;
    @Column(name = "titulo_coleccion", nullable = false)
    private String tituloColeccion;
    @Column(name = "cantidad_hechos", nullable = false)
    private Long cantidadHechos;
    @Column(name = "provincia", nullable = false)
    private String provincia;
}
