package ar.edu.frba.ddsi.servicio_agregador.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "nombre", nullable = false)
  private String nombre;
  @Column(name = "apellido", nullable = false)
  private String apellido;
  @Column(name = "fecha_nacimiento", nullable = false, unique = true)
  private LocalDate fechaNacimiento;
  //private Rol rol;
}
