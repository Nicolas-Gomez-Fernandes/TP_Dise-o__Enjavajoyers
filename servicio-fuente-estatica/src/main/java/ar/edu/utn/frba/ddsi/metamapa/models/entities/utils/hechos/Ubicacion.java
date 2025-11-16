package ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class Ubicacion {
    @Column(name = "latitud", nullable = false)
    public Double latitud;
    @Column(name = "longitud", nullable = false)
    public Double longitud;
    /*public String provincia;
    public String ciudad;
    public String municipio;*/

    public Ubicacion(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

}
