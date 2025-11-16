package ar.edu.frba.ddsi.servicio_agregador.models.repositories.impl;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.Fuente;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.fuentes.OrigenFuente;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.IHechosRepository;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho ;
import lombok.Getter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;


public class HechosRepository{
  @Getter
  private Map<Long, Hecho> hechosById; // ID de agregador
  private AtomicLong idGenerator = new AtomicLong(1);

  public HechosRepository() {
    this.hechosById = new HashMap<>();
  }


  public Hecho save(Hecho hecho) {
    //  "se considerará que un hecho está repetido si el “título” es el mismo. De ser así, se pisarán los atributos del existente."

    if (hecho.getId() == null) {
      hecho.setId(idGenerator.getAndIncrement());
    }

    return hechosById.put(hecho.getId(), hecho);
  }

  public void saveAll(List<Hecho> hechos) {
    hechos.forEach(this::save);
  }

  public List<Hecho> findHechosByFuente(OrigenFuente f) {
    return hechosById.values().stream().filter(h -> h.getFuente().equals(f)).collect(Collectors.toList());
  }


  public Hecho findByExternalIdAndFuente(Long externalId, Fuente fuente) {
    return hechosById.values().stream().filter(h -> h.getFuente().equals(fuente) && h.getExternalId().equals(externalId)).findFirst().orElse(null);
  }


  public void delete(Hecho hecho) {
    hechosById.remove(hecho.getId());
  }

  public List<Hecho> findAll() {
    return new ArrayList<>(this.hechosById.values());
  }

  public Hecho findByTitulo(String titulo) {
    return hechosById.values().stream().filter(h -> h.getTitulo().equals(titulo)).findFirst().orElse(null);
  }


}
