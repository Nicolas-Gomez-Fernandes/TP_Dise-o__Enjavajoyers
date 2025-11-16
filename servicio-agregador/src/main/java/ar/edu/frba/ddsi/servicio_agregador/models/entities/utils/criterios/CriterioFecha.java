package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios;



import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@DiscriminatorValue("criterio_fecha")
public class CriterioFecha extends ICriterioPertenencia {
    @Column(name = "fecha_desde", columnDefinition = "DATE")
    private LocalDateTime desde;
    @Column(name = "fecha_hasta",columnDefinition = "DATE")
    private LocalDateTime hasta;

    public CriterioFecha(LocalDateTime desde, LocalDateTime hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    public List<Hecho> aplicarCriterio(List<Hecho> hechos) {
        return hechos.stream().filter(this::cumpleCondicion).collect(Collectors.toList());
    }


    @Override
    public boolean cumpleCondicion(Hecho hecho) {
        return hecho.getFechaAcontecimiento()
            .isAfter(desde) && hecho.getFechaAcontecimiento().isBefore(hasta);
    }
}
