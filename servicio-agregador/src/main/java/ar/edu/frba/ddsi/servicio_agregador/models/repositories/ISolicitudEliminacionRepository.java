package ar.edu.frba.ddsi.servicio_agregador.models.repositories;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.Estado;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.SolicitudEliminacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ISolicitudEliminacionRepository extends JpaRepository<SolicitudEliminacion, Long> {

    @Query("SELECT COUNT(*) FROM SolicitudEliminacion s WHERE s.estado = :spam")
    Integer countByEstado(Estado spam);

    @Query("SELECT COUNT(*) FROM SolicitudEliminacion s")
    Integer cantidadTotalDeSolicitudes();
}
