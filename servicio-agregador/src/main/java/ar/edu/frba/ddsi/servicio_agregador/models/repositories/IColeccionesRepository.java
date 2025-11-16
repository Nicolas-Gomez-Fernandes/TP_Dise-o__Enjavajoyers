package ar.edu.frba.ddsi.servicio_agregador.models.repositories;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.ColeccionOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Coleccion;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.querysDTO.IProvinciaCantidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface IColeccionesRepository extends JpaRepository<Coleccion, Long> {

  @Query("SELECT h.provincia AS provincia, COUNT(h) AS cantidad FROM Coleccion c JOIN c.hechos h WHERE c.id = :id GROUP BY h.provincia ORDER BY COUNT(h) DESC")
  List<IProvinciaCantidad> provinciasConMasHechosEnColeccion(Long id);

  Collection<Coleccion> findTop5ByOrderByIdDesc();
}
