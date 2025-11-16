package ar.edu.frba.ddsi.servicio_estadistica.repositories;

import ar.edu.frba.ddsi.servicio_estadistica.models.entities.EstadisticaColeccion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IColeccionRepository extends JpaRepository<EstadisticaColeccion, Long> {
    EstadisticaColeccion findByIdColeccion(Long idColeccion);

    @Query("SELECT e FROM EstadisticaColeccion e ORDER BY e.cantidadHechos desc")
    List<EstadisticaColeccion> findLimited(Pageable pageable);

    @Query("SELECT e.idColeccion, e.tituloColeccion,e.provincia,e.cantidadHechos FROM EstadisticaColeccion e ORDER BY e.cantidadHechos DESC")
    List<Object[]> findAllExport();
}