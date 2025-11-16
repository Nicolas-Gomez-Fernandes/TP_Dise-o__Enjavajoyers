package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import org.springframework.stereotype.Component;

import java.util.List;


public interface IAlgoritmoConsenso {
    void aplicarConsenso(Hecho hecho, List<Hecho> hechosAComparar,Integer cantDeFuentes);

    TipoAlgoritmoDeConsenso getTipoDeAlgoritmo();
}
