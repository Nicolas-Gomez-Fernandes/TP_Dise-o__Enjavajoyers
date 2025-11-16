package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EvaluadorDeConsenso {
  private List<IAlgoritmoConsenso> algoritmos;

  public EvaluadorDeConsenso(List<IAlgoritmoConsenso> algoritmos) {
      this.algoritmos = algoritmos;
  }

  public void aplicarConsenso(List<Hecho> hechos,Integer cantDeFuentes){
    hechos.forEach(h -> algoritmos.forEach(algoritmo -> algoritmo.aplicarConsenso(h,hechos, cantDeFuentes)));
  }

}
