package ar.edu.frba.ddsi.servicio_agregador.services;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.CategoriaOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import java.util.List;

public interface ICategoriasService {
    CategoriaOutputDTO buscarPorId(Long id);
    List<CategoriaOutputDTO> buscarTodos();
    Long crear(String nombre);
    void eliminar(Long id);

    Categoria buscarPorNombreOCrear(String nombre);

  Categoria buscarPorNombre(String categoria);
}
