package ar.edu.utn.frba.ddsi.metamapa.models.entities.adapters;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Ubicacion;

public interface IClimaAdapter {
  Ubicacion getUbicacion(Double latitud, Double longitud);
}
