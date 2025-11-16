package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios;


import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Entity
@Table(name = "criterios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_criterio")
@Getter
public abstract class ICriterioPertenencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public abstract List<Hecho> aplicarCriterio(List<Hecho> hechos);

    public abstract boolean  cumpleCondicion(Hecho hecho);
}
