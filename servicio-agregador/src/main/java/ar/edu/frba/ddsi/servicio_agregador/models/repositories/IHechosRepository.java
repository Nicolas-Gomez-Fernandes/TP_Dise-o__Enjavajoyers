package ar.edu.frba.ddsi.servicio_agregador.models.repositories;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.Fuente;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.fuentes.OrigenFuente;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.querysDTO.IHoraCantidad;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.querysDTO.IProvinciaCantidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IHechosRepository extends JpaRepository<Hecho, Long>, JpaSpecificationExecutor<Hecho> {

  List<Hecho> findHechosByFuente(OrigenFuente f);

  boolean existsByTitulo(String titulo);

  List<Hecho> findAllByCategoria(Categoria categoria);

  @Query("SELECT COUNT(h) FROM Hecho h WHERE h.categoria = :c")
  Integer cantHechosDeCategoria(Categoria c);

  @Query("SELECT h.provincia AS provincia, COUNT(h) AS cantidad FROM Hecho h WHERE h.categoria = :c GROUP BY h.provincia ORDER BY COUNT(h) DESC")
  List<IProvinciaCantidad> provinciasConMasHechosEnCategoria(Categoria c);

  @Query("SELECT HOUR(h.fechaAcontecimiento) AS hora, COUNT(h) AS cantidad FROM Hecho h WHERE h.categoria = :c GROUP BY HOUR(h.fechaAcontecimiento) ORDER BY COUNT(h) DESC")
  List<IHoraCantidad> cantidadDeHechosPorHora(Categoria c);

  @Query("SELECT h FROM Hecho h WHERE h.provincia IS NULL OR h.provincia = ''")
  List<Hecho> findHechosSinProvincia();

  boolean existsByExternalIdAndFuente_Fuente(Long id, Fuente fuente);

  Optional<Hecho> findByExternalIdAndFuente_Fuente(Long id, Fuente tipoFuente);

  @Query("SELECT DISTINCT h.provincia FROM Hecho h WHERE h.provincia IS NOT NULL")
  List<String> findProvinciasDeHechos();
}
