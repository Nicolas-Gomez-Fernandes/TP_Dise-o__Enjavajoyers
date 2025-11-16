package ar.edu.frba.ddsi.servicio_estadistica.repositories;

import ar.edu.frba.ddsi.servicio_estadistica.models.entities.EstadisticaCategoria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoriaRepository extends JpaRepository<EstadisticaCategoria, Long> {
  EstadisticaCategoria findByCategoria(String categoria);

   @Query("SELECT e FROM EstadisticaCategoria e ORDER BY e.categoria desc")
  List<EstadisticaCategoria> findLimited(PageRequest of);

   //@Query("SELECT e FROM  EstadisticaCategoria e ORDER BY e.cantidad_hechos_categoria desc")
   Optional<EstadisticaCategoria> findTopByOrderByCantidadHechosCategoriaDesc();

    @Query("SELECT e.categoria, e.provincia, e.cantidadHechosProvincia, e.hora, e.cantidadHechosHora FROM EstadisticaCategoria e ORDER BY e.categoria DESC")
    List<Object[]> findAllExport();

}
