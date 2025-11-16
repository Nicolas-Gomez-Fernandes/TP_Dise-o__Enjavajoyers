package ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fuentes")
@Entity
public class Fuente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "csv_path", nullable = false)
  private String csvPath;

  public Fuente(String origen) {
  }
}
