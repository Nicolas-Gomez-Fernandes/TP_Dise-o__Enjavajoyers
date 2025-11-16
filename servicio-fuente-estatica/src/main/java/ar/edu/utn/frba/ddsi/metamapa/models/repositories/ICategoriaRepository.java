package ar.edu.utn.frba.ddsi.metamapa.models.repositories;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
  boolean existsByNombre(String nombre);
  Categoria findByNombre(String nombre);
}
