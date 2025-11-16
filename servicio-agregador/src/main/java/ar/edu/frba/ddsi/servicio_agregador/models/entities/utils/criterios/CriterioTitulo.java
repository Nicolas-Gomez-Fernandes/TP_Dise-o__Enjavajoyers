package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios;



import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@DiscriminatorValue("criterio_titulo")
public class CriterioTitulo extends ICriterioPertenencia {
    @Column(name = "palabra_clave", length = 20)
    private String palabraClave;

    public CriterioTitulo(String palabraClave) {
        this.palabraClave = palabraClave.toLowerCase();
    }

    @Override
    public List<Hecho> aplicarCriterio(List<Hecho> hechos) {
        return hechos.stream()
            .filter(h -> h.getTitulo().toLowerCase().contains(palabraClave)).collect(Collectors.toList());
    }

    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getTitulo().toLowerCase().contains(palabraClave);
    }
}