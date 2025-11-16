package ar.edu.frba.ddsi.servicio_agregador.models.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.ICriterioPertenencia;


@Repository
public interface ICriteriosPertenenciaRepository extends JpaRepository<ICriterioPertenencia, Long>{
}
