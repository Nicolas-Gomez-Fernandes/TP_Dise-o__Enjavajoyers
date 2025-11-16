package ar.edu.frba.ddsi.servicio_agregador.models.entities.fuentes;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.Fuente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "origen_fuente")
public class OrigenFuente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "fuente")
    Fuente fuente;
    @Column(name = "subfuente")
    String subfuente;

  public OrigenFuente(Fuente fuente, String subfuente) {
    this.fuente = fuente;
    this.subfuente = subfuente;
  }
}
