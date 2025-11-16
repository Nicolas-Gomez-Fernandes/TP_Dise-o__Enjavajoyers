package ar.edu.utn.frba.ddsi.metamapa.services.impl;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Categoria;
import ar.edu.utn.frba.ddsi.metamapa.models.repositories.ICategoriaRepository;
import ar.edu.utn.frba.ddsi.metamapa.services.ICategoriaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService implements ICategoriaService{
  private ICategoriaRepository repository;

  public CategoriaService(ICategoriaRepository repository) {
    this.repository = repository;
  }

  @Override
  public Categoria crearCategoria(String nombre){
    if(!this.repository.existsByNombre(nombre)){
      Categoria categoria = new Categoria(nombre);
      return this.repository.save(categoria);
    };
    return this.repository.findByNombre(nombre);
  }

  @Override
  public void eliminarCategoria(Long id) {
    this.repository.deleteById(id);
  }

  @Override
  public Categoria buscarOcrearCategoria(String nombre) {
    Categoria categoria = this.repository.findByNombre(nombre);
    if (categoria == null) {
      categoria = this.crearCategoria(nombre);
      //throw new EntityNotFoundException("Categoría no encontrada con nombre: " + nombre);
    }
    return categoria;
  }


}
