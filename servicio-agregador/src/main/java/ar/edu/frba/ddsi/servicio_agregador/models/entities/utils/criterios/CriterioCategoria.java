package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios;



import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("criterio_categoria")
public class CriterioCategoria extends ICriterioPertenencia {
   @ManyToOne
    Categoria categoria;

    @Override
    public List<Hecho> aplicarCriterio(List<Hecho> hechos) {
        return hechos.stream()
                .filter(h -> h.getCategoria() == categoria).toList();
    }

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getCategoria() == categoria;
    }
}
