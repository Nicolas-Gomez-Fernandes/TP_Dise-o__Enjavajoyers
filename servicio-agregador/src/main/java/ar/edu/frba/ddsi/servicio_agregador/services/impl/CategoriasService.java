package ar.edu.frba.ddsi.servicio_agregador.services.impl;

import ar.edu.frba.ddsi.servicio_agregador.services.ICategoriasService;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.ICategoriasRepository;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.CategoriaOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;


@Slf4j
@Service
public class CategoriasService implements ICategoriasService {
    private ICategoriasRepository categoriasRepository;

    public CategoriasService(ICategoriasRepository categoriasRepository) {
        this.categoriasRepository = categoriasRepository;
    }

    @Override
    public CategoriaOutputDTO buscarPorId(Long id) {
        Categoria categoria = this.categoriasRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada con id: " + id));
        return this.categoriaOutputDTO(categoria);
    }

    @Override
    public List<CategoriaOutputDTO> buscarTodos(){
        return this.categoriasRepository
        .findAll()
        .stream()
        .map(this::categoriaOutputDTO)
        .toList();
    }

    @Override
    public Long crear(String nombre){
        if(nombre == null){
            throw new IllegalArgumentException("El nombre de la categoria no puede ser nulo");
        }
        Categoria categoria = new Categoria(nombre);
        this.categoriasRepository.save(categoria);
        return categoria.getId();
    }    

    @Override
    public void eliminar(Long id){
        if(id == null){
            throw new IllegalArgumentException("El id de la categoria no puede ser nulo");
        }
        boolean existe = this.categoriasRepository.existsById(id);
        if(!existe){
            throw new IllegalArgumentException("Categoria no encontrada con id: " + id);
        }
        this.categoriasRepository.deleteById(id);
    }

    @Override
    public Categoria buscarPorNombreOCrear(String nombre) {
        return this.categoriasRepository.findByNombre(nombre).orElseGet(() -> {
            Categoria categoria = new Categoria(nombre);
            log.info("Creando nueva categoria: {}", nombre);
            return this.categoriasRepository.save(categoria);
        });
    }

  @Override
  public Categoria buscarPorNombre(String categoria) {
    return this.categoriasRepository.findByNombre(categoria)
        .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada con nombre: " + categoria));
  }


  private CategoriaOutputDTO categoriaOutputDTO(Categoria categoria) {
        return new CategoriaOutputDTO(
            categoria.getId(),
            categoria.getNombre());
      }
}
