package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos;

import static ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.TipoAlgoritmoDeConsenso.MULTIMPLES_MENCIONES;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlgoritmoMultiplesMenciones implements IAlgoritmoConsenso {
    @Getter
    private TipoAlgoritmoDeConsenso tipo = MULTIMPLES_MENCIONES;

    public void aplicarConsenso(Hecho hecho, List<Hecho> hechosAComparar, Integer cantDeFuentes){
        long cantVecesQueSeRepite = hechosAComparar.stream().
            filter(h->h.
                equals(hecho)). //equals modificado para verificar que un hecho es igual a otro
                count();
        long cantHechosDistintosMismoNombre = hechosAComparar.stream().
            filter(h->h.
                mismoTituloDistintoHecho(hecho)).
                count();
        hecho.setTieneConsenso(MULTIMPLES_MENCIONES,cantVecesQueSeRepite > 0 && cantHechosDistintosMismoNombre == 0); //si se repite
    }

  @Override
  public TipoAlgoritmoDeConsenso getTipoDeAlgoritmo() {
    return this.tipo;
  }
}
