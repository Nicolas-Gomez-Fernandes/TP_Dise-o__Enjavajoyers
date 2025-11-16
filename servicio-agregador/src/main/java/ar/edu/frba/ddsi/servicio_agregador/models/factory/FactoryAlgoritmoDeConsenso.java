package ar.edu.frba.ddsi.servicio_agregador.models.factory;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.AlgoritmoAbsoluto;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.IAlgoritmoConsenso;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.AlgoritmoMayoriaSimple;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.AlgoritmoMultiplesMenciones;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.TipoAlgoritmoDeConsenso;
import org.springframework.stereotype.Component;

@Component
public class FactoryAlgoritmoDeConsenso {

  public static IAlgoritmoConsenso crearAlgoritmo(TipoAlgoritmoDeConsenso tipo) {

    return switch (tipo) {
      case ABSOLUTO -> new AlgoritmoAbsoluto();
      case MAYORIA_SIMPLE -> new AlgoritmoMayoriaSimple();
      case MULTIMPLES_MENCIONES -> new AlgoritmoMultiplesMenciones();
      default -> throw new RuntimeException("Tipo de algoritmo no valido");
    };
  }

}
