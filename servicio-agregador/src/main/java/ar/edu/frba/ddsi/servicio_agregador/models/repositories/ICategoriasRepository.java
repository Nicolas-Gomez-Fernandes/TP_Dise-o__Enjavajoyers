package ar.edu.frba.ddsi.servicio_agregador.models.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import java.util.Optional;


@Repository
public interface ICategoriasRepository extends JpaRepository<Categoria, Long>{
    Optional<Categoria> findByNombre(String nombre);
}
