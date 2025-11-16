package ar.edu.frba.ddsi.servicio_agregador.models.entities;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.fuentes.OrigenFuente;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.TipoAlgoritmoDeConsenso;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Multimedia;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Ubicacion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "hecho")
public class Hecho {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // propio
  @Column(name = "external_id", nullable = false)
  private Long externalId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fuente_id", nullable = false)
  private OrigenFuente fuente;

  @Column(name = "titulo", nullable = false)
  private String titulo;

  @Column(name = "descripcion", nullable = false, length = 500)
  private String descripcion;

  @ManyToOne
  @JoinColumn(name = "categoria_id", nullable = false)
  private Categoria categoria;

  @Embedded
  private Ubicacion ubicacion;

  @Column(name = "provincia")
  private String provincia; // para facilitar queries y filtros TODO falta implementacion de apiGeoref para obtener las provincias a partir de long y lat

  @Column(name = "fecha_creacion", nullable = false)
  private LocalDateTime fechaCreacion;

  @Column(name = "fecha_acontecimiento", nullable = false)
  private LocalDateTime fechaAcontecimiento;

  @Column(name = "fecha_ultima_edicion")
  private LocalDateTime fechaUltimaEdicion;

  @OneToMany (mappedBy = "hecho", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<Multimedia> archivosMultimedia = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "etiquetas", joinColumns = @JoinColumn(name = "hecho_id", referencedColumnName = "id"))
  private List<String> etiquetas = new ArrayList<>(); //Por ahora lo pense asi, y cuando se aplica una etiqueta se la agrega a esa lista, para despues poder filtar por las etiquetas como si fuera #

  @Column(name = "eliminado", nullable = false)
  private Boolean eliminado;

  @ElementCollection()
  @CollectionTable(name = "hecho_consenso", joinColumns = @JoinColumn(name = "hecho_id", referencedColumnName = "id"))
  private List<TipoAlgoritmoDeConsenso> tieneConsenso = new ArrayList<>();

  // TODO -- cambiar a unidireccional

  //@ManyToMany(mappedBy = "hechos")
  //private List<Coleccion> colecciones;

  public Hecho(String titulo, String descripcion, Categoria categoria, Ubicacion ubicacion, LocalDateTime fecha) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.ubicacion = ubicacion;
    this.fechaAcontecimiento = fecha;

    this.fechaCreacion = LocalDateTime.now();
    this.eliminado = false;
  }

  public Hecho() {
    // setea las colecciones vacias para evitar null pointer exceptions
    //TODO UNIDIRECCIONAL this.colecciones = new ArrayList<>();
    this.tieneConsenso = new ArrayList<>();
    this.archivosMultimedia = new ArrayList<>();
    this.etiquetas = new ArrayList<>();
  }

  public Boolean estaConsensuadoPara(TipoAlgoritmoDeConsenso algoritmo) {
    return tieneConsenso.contains(algoritmo);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Hecho hecho = (Hecho) o;
    return Objects.equals(getTitulo(), hecho.getTitulo()) &&  Objects.equals(getUbicacion(), hecho.getUbicacion());
  }
  public boolean mismoTituloDistintoHecho(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Hecho hecho = (Hecho) o;
    return Objects.equals(getTitulo(), hecho.getTitulo()) &&  !Objects.equals(getUbicacion(), hecho.getUbicacion());
  }

  public void setTieneConsenso(TipoAlgoritmoDeConsenso tipoAlgoritmoDeConsenso, boolean b){
    if(!estaConsensuadoPara(tipoAlgoritmoDeConsenso) && b){
      this.getTieneConsenso().add(tipoAlgoritmoDeConsenso);
    }
    else if(estaConsensuadoPara(tipoAlgoritmoDeConsenso) && !b && !tieneConsenso.isEmpty()){
      this.getTieneConsenso().remove(tipoAlgoritmoDeConsenso);
    }

  }
}
