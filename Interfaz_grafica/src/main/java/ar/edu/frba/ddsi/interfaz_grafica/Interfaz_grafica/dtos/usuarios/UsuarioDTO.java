package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.usuarios;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.rolesYPermisos.Rol;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UsuarioDTO {
  private Long id;
  private String nombre;
  private String apellido;
  private String email;
  private String contrasenia;
  private LocalDate fechaNacimiento;
  private Rol rol;


  public UsuarioDTO(Rol rol) {
    this.rol = rol;
  }
}
