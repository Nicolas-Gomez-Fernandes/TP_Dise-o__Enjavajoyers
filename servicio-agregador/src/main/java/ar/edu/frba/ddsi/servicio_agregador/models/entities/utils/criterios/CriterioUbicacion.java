package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios;



import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
@DiscriminatorValue("criterio_ubicacion")
public class CriterioUbicacion extends ICriterioPertenencia {
    @Column(name = "latitud", columnDefinition = "DOUBLE")
    private Double latitud;
    @Column(name = "longitud", columnDefinition = "DOUBLE")
    private Double longitud;

    public CriterioUbicacion(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    @Override
    public List<Hecho> aplicarCriterio(List<Hecho> hechos) {
        return hechos.stream().filter(h->h.getUbicacion().latitud.equals(latitud)
                                        && h.getUbicacion().longitud.equals(longitud))
                                        .toList();
    }

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getUbicacion().latitud.equals(latitud) && hecho.getUbicacion().longitud.equals(longitud);
    }
}
