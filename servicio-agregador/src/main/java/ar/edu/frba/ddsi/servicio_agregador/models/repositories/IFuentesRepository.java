package ar.edu.frba.ddsi.servicio_agregador.models.repositories;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.Fuente;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.fuentes.OrigenFuente;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFuentesRepository extends JpaRepository<OrigenFuente, Long> {
  Optional<OrigenFuente> findBySubfuente(String subfuente);

  boolean existsBySubfuente(String fuente);

  boolean existsByFuenteAndSubfuente(Fuente tipoFuente, String subfuente);
}
