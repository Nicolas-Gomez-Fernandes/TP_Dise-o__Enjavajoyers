package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.rolesYPermisos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolesYPermisosDTO {
  private String username;
  private Rol rol;
  private List<Permiso> permisos;
}
