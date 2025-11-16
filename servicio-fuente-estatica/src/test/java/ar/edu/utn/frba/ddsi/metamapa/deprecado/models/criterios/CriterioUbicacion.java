package ar.edu.utn.frba.ddsi.metamapa.deprecado.models.criterios;



import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;

import java.util.List;

public class CriterioUbicacion implements CriterioPertenencia {
    private Double latitud;
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
}
