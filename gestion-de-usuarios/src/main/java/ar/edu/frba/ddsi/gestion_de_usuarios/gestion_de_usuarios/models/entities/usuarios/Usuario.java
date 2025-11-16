package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "usuarios")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "nombre", nullable = false)
  private String nombre;
  @Column(name = "apellido")
  private String apellido;
  @Column(name = "email", nullable = false)
  private String email;
  @Column(name = "fecha_nacimiento")
  private LocalDate fechaNacimiento;
  @Column(name = "contrasenia", nullable = false)
  private String contrasenia;
  @Column(name = "rol", nullable = false)
  private TipoRol rol;
  @ElementCollection(fetch = FetchType.EAGER)
  private List<Permiso> permisos;
}
