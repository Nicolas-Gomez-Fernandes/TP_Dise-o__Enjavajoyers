package ar.edu.utn.frba.ddsi.metamapa.deprecado.models;


import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import ar.edu.utn.frba.ddsi.metamapa.deprecado.models.criterios.CriterioPertenencia;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Coleccion {
  private String nombre;
  private String descripcion;
  @Getter
  private List<Hecho> hechos;
  private List<CriterioPertenencia> criterios;
  @Setter
  private FuenteDatos fuente; //TODO revisar y cambiar
  //public EstaticaCSV fuenteEstatica;


  public Coleccion(String descripcion, String nombre) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.hechos = new ArrayList<Hecho>();
    this.fuente = null;
    this.criterios = new ArrayList<CriterioPertenencia>();
  }

  public void agregarCriterioDePertenencia(CriterioPertenencia criterio){
    this.criterios.add(criterio);
    this.aplicarCriteriosDePertenencia(); //siempre que se agregue un nuevo criterio se aplica el criterio
  }

  public void aplicarCriteriosDePertenencia(){
    criterios.forEach(c ->{
      this.hechos = c.aplicarCriterio(this.hechos);});
  }

  public void eliminarCriterio(CriterioPertenencia criterio) throws IOException { //elimina el criterio de la lista recalcula la lista y vuelve aplicar criterios
    this.criterios.remove(criterio);
    this.hechos = this.fuente.cargarDatos();
    this.aplicarCriteriosDePertenencia();
  }

  // Asocia la fuente y la carga a la coleccion
  public void asociarFuente(FuenteDatos fuente) throws IOException {
    this.fuente = fuente;
    this.hechos = fuente.cargarDatos();
  }

  // Devuelve el hecho numero index
  public Hecho getHechoIndex(Integer index) {
    return this.hechos.get(index);
  }

  public void agregarHecho(Hecho hecho) {
    this.hechos.add(hecho);
  }

  public void eliminarHecho(Hecho hecho) {
    this.hechos.remove(hecho);
  }
}
