package ar.edu.utn.frba.ddsi.metamapa.models.repositories.impl;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import ar.edu.utn.frba.ddsi.metamapa.models.repositories.IHechosRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HechosRepository {
  private Map<Long, Hecho> hechos;

  public HechosRepository() {
    this.hechos = hechos = new HashMap<>();
  }

  // @Override
  public Hecho save(Hecho newHecho) {
    //  "se considerará que un hecho está repetido si el “título” es el mismo. De ser así, se pisarán los atributos del existente."
    // remueve el hecho repetido por titulo
    Hecho hechoRepetido = buscarHechoPorTitulo(newHecho.getTitulo());

    if (hechoRepetido != null) {
      // Actualiza el hecho existente
      this.update(hechoRepetido.getId(), newHecho);
      return null;
    }
    // Genera un UD/UUID para el nuevo hecho si no tiene
    // Genera un ID para el nuevo hecho

    /*Long id = (long) (this.hechos.size() + 1);
    newHecho.setId(id);
    hechos.put(id, newHecho);*/
    return null;
  }

  private Hecho buscarHechoPorTitulo(String titulo) {
    return hechos.values().stream()
        .filter(h -> h.getTitulo().equals(titulo))
        .findFirst()
        .orElse(null);
  }

  public void saveAll(List<Hecho> newHechos) {
    newHechos.forEach(this::save);
  }

  // @Override
  public void delete(Hecho hecho) {
    hechos.remove(hecho);
  }

  // @Override
  public List<Hecho> findAll() {
    return new ArrayList<>(this.hechos.values());
  }

  public void update(Long id, Hecho hecho) {
    if (hechos.containsKey(id)) {
      hecho.setId(id);  // Mantener el mismo ID
      hechos.put(id, hecho);
    }
  }
}

