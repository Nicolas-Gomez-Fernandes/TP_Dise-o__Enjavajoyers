package ar.edu.utn.frba.ddsi.metamapa.models.repositories;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHechosRepository extends JpaRepository<Hecho, Long> {
  Hecho save(Hecho hecho);
  void delete(Hecho hecho);
  List<Hecho> findAll();

  @Query("SELECT h.titulo FROM Hecho h")
  List<String> findAllTitulos();
}
