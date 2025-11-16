package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos;

import static ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.TipoAlgoritmoDeConsenso.MAYORIA_SIMPLE;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlgoritmoMayoriaSimple implements IAlgoritmoConsenso {
    @Getter
    private TipoAlgoritmoDeConsenso tipo = MAYORIA_SIMPLE;

    public void aplicarConsenso(Hecho hecho, List<Hecho> hechosAComparar, Integer cantDeFuentes) {
        long cantVecesQueSeRepite = hechosAComparar.stream().
            filter(h->h.
                equals(hecho)). //equals modificado para verificar que un hecho es igual a otro
                count();

        hecho.setTieneConsenso(MAYORIA_SIMPLE,cantVecesQueSeRepite+1 > cantDeFuentes/2);//el mas 1 esta porque cuando comparas el hecho contra los demas no estas contando la fuente del hecho mismo
    }

  @Override
  public TipoAlgoritmoDeConsenso getTipoDeAlgoritmo() {
    return this.tipo;
  }
}
