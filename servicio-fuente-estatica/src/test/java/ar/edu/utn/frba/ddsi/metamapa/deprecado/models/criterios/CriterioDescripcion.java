
package ar.edu.utn.frba.ddsi.metamapa.deprecado.models.criterios;


import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;

import java.util.List;
import java.util.stream.Collectors;

public class CriterioDescripcion implements CriterioPertenencia {
        private String palabraClave;

        public CriterioDescripcion(String palabraClave) {
            this.palabraClave = palabraClave.toLowerCase();
        }

        @Override
        public List<Hecho> aplicarCriterio(List<Hecho> hechos) {
            return hechos.stream()
                    .filter(h -> h.getDescripcion().toLowerCase().contains(palabraClave)).collect(Collectors.toList());
        }
}

