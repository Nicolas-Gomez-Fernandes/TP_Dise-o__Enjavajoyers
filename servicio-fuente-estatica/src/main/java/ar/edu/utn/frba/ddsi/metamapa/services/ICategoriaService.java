package ar.edu.utn.frba.ddsi.metamapa.services;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Categoria;

public interface ICategoriaService {
  Categoria crearCategoria(String nombre);
  void eliminarCategoria(Long id);
  Categoria buscarOcrearCategoria(String nombre);

}
