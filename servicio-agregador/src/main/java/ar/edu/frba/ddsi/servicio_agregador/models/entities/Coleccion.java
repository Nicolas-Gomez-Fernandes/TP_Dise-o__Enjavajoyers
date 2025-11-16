package ar.edu.frba.ddsi.servicio_agregador.models.entities;


import ar.edu.frba.ddsi.servicio_agregador.models.entities.fuentes.OrigenFuente;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.TipoAlgoritmoDeConsenso;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.ICriterioPertenencia;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratedColumn;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IdGeneratorType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "colecciones")
public class Coleccion {
  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  @Column(length = 32)
  private Long id; // handle

  @Column(name = "titulo", nullable = false)
  private String titulo;
  @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
  private String descripcion;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "coleccion_hechos",
      joinColumns = @JoinColumn(name = "coleccion_id"),
      inverseJoinColumns = @JoinColumn(name = "hecho_id")
  )
  private List<Hecho> hechos;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "coleccion_id")
  private List<ICriterioPertenencia> criterios;

  @Enumerated(EnumType.STRING)
  @JoinTable(
      name = "coleccion_algoritmo",
      joinColumns = @JoinColumn(name = "coleccion_id"),
      inverseJoinColumns = @JoinColumn(name = "algoritmo")
  )
  private TipoAlgoritmoDeConsenso algoritmo;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "coleccion_fuentes",
      joinColumns = @JoinColumn(name = "coleccion_id"),
      inverseJoinColumns = @JoinColumn(name = "fuente_id")
  )
  private List<OrigenFuente> fuentes;
  //TODO ver el tema permisos
  //private Usuario creador;


  public Coleccion(String descripcion, String nombre){
    this.titulo = nombre;
    this.descripcion = descripcion;
    this.hechos = new ArrayList<Hecho>();
    this.fuentes = new ArrayList<OrigenFuente>();
    this.criterios = new ArrayList<ICriterioPertenencia>();
    //this.creador = new Usuario();
  }

  public void agregarCriterioDePertenencia(ICriterioPertenencia criterio){
    if (!this.criterios.contains(criterio)) {
      this.criterios.add(criterio);
      this.aplicarCriterioDePertenencia(criterio);
    }
    //siempre que se agregue un nuevo criterio se aplica el criterio
  }

  public void aplicarCriterioDePertenencia(ICriterioPertenencia criterio){
    this.hechos = this.hechos.stream().filter(criterio::cumpleCondicion).collect(Collectors.toList());
  }

  public void aplicarCriteriosDePertenencia(){
    this.criterios.forEach(this::aplicarCriterioDePertenencia);
  }

  // Asocia la fuente y la carga a la coleccion
  public void asociarFuente(OrigenFuente fuente){
    this.fuentes.add(fuente);
  }

  public void asociarFuentes(List<OrigenFuente> fuentes) {
    this.fuentes.addAll(fuentes);
  }


  public void agregarHecho(Hecho hecho) {
    this.hechos.add(hecho);
  }

  public void eliminarHecho(Hecho hecho) {
    this.hechos.remove(hecho);
  }

  public void agregarHechos(List<Hecho> listaHechos){
    this.hechos.addAll(listaHechos);
  }

  public List<Hecho> getHechos(boolean consensuado) {
    //devuelve todos los hechos de la coleccion si es modo irrestricto
    if (!consensuado) {
      return this.hechos;
    }

    //si consensuado es true, filtrar solo los que cumplen con el algoritmo de consenso de la coleccion
    return hechos.stream()
        .filter(hecho ->
            hecho.estaConsensuadoPara(algoritmo))
        .collect(Collectors.toList());
  }

  public void modificarAlgoritmoDeConsenso(TipoAlgoritmoDeConsenso algoritmo) {
    this.algoritmo = algoritmo;
  }

  public void desAsociarFuente(OrigenFuente fuente) {
    this.fuentes.remove(fuente);
  }
}
