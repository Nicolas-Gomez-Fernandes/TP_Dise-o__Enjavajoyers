package ar.edu.utn.frba.ddsi.metamapa.models.repositories;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Fuente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFuentesRepository extends JpaRepository<Fuente, Long> {
  boolean existsByCsvPath(String csvPath);
  Fuente findByCsvPath(String path);
}
