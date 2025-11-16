package ar.edu.frba.ddsi.servicio_estadistica.repositories;


import ar.edu.frba.ddsi.servicio_estadistica.models.entities.EstadisticaSolicitudDeEliminacionSpam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ISolicitudSpamRepository extends JpaRepository<EstadisticaSolicitudDeEliminacionSpam, Long> {

  @Modifying
  @Transactional
  @Query("UPDATE EstadisticaSolicitudDeEliminacionSpam e SET e.cantidadSolicitudesEliminacion = :cantTotalDeSolicitudes, e.cantidadDESpam = :cantDeSpam WHERE e.id=1")
  void actualizar(Long cantDeSpam, Long cantTotalDeSolicitudes);
}
