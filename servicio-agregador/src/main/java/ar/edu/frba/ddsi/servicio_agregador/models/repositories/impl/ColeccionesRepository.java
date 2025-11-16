package ar.edu.frba.ddsi.servicio_agregador.models.repositories.impl;


import ar.edu.frba.ddsi.servicio_agregador.models.entities.Coleccion;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.IColeccionesRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Repository
public class ColeccionesRepository{
  private final List<Coleccion> colecciones;

  public ColeccionesRepository() {
    this.colecciones = new ArrayList<Coleccion>();
  }


  public Coleccion buscarPorId(String id) {
    return this.colecciones.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
  }


  /*public void save(Coleccion coleccion) {
    coleccion.setId(UUID.randomUUID().toString());
    this.colecciones.add(coleccion);
  }*/


  public List<Coleccion> buscarTodos() {
    return this.colecciones;
  }


  public void delete(Coleccion coleccion) {
    this.colecciones.remove(coleccion);
  }

}
