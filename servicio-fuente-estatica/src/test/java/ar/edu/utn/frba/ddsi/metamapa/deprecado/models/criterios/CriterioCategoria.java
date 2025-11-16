package ar.edu.utn.frba.ddsi.metamapa.deprecado.models.criterios;



import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Categoria;

import java.util.List;

public class CriterioCategoria implements CriterioPertenencia{
    Categoria categoria;

    public CriterioCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public List<Hecho> aplicarCriterio(List<Hecho> hechos) {
        return hechos.stream()
                .filter(h -> h.getCategoria() == categoria).toList();
    }
}
