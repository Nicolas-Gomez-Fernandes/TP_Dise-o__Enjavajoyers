package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.dtos;

import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios.Permiso;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios.TipoRol;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class UsuarioOutputDTO {
  private Long id;
  private String nombre;
  private String apellido;
  private String email;
  private LocalDate fechaNacimiento;
  private TipoRol rol;
  private List<Permiso> permisos;
}
