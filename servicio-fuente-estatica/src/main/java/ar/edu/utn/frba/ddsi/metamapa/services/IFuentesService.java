package ar.edu.utn.frba.ddsi.metamapa.services;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Fuente;

public interface IFuentesService{
  Fuente createFuente(String path);
  void deleteFuente(Long id);
}
