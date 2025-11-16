package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Embeddable
public class Ubicacion {

  @Column(name = "latitud", nullable = false)
  public Double latitud;
  @Column(name = "longitud", nullable = false)
  public Double longitud;



  public Ubicacion(Double latitud, Double longitud) {
    this.longitud = longitud;
    this.latitud = latitud;
  }
}
