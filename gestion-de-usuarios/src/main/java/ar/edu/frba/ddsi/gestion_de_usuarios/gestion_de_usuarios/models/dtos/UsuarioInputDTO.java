package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.dtos;


import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios.Permiso;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios.TipoRol;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UsuarioInputDTO {
  private String nombre;
  private String apellido;
  private String email;
  private TipoRol rol;
  private LocalDate fecha_nacimiento;
  private String contrasenia;
}
