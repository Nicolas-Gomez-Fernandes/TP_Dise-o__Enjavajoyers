package ar.edu.utn.frba.ddsi.metamapa.models.entities;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Categoria;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Fuente;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Ubicacion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hechos")
public class Hecho {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String titulo;
  @Column(nullable = false, length = 500)
  private String descripcion;
  @ManyToOne
  @JoinColumn(name = "categoria_id", referencedColumnName="id")
  private Categoria categoria;
  @Column(nullable = false)
  private LocalDateTime fecha;
  @Column(nullable = false)
  private LocalDateTime fechaCreacion;

  @Embedded
  private Ubicacion ubicacion;

  // TODO - hacerlo clase Entidad y relacionarlo many to many
  // private List<String> etiquetas; //Por ahora lo pense asi, y cuando se aplica una etiqueta se la agrega a esa lista, para despues poder filtar por las etiquetas como si fuera #

  @ManyToOne
  @JoinColumn(name = "fuente_id") // FK en la tabla hechos
  private Fuente fuente; //El path del archivo o la url de donde se obtuvo el hecho
  @Column(nullable = false)
  private Boolean eliminado;


  public Hecho(String titulo, String descripcion, Categoria categoria, Ubicacion ubicacion, LocalDateTime fecha) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.ubicacion = ubicacion;
    this.fecha = fecha;
  }


}
