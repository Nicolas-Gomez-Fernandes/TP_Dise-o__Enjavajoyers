package ar.edu.utn.frba.ddsi.metamapa.deprecado.models.criterios;


import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;

import java.util.List;

public interface CriterioPertenencia {

    List<Hecho> aplicarCriterio(List<Hecho> hechos);
}
