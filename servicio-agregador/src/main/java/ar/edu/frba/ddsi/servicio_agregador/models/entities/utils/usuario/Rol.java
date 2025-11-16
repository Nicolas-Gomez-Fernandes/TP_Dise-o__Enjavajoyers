package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.usuario;

import java.util.List;

public class Rol {
  private String nombre;
  private List<Permiso> permisos;

  public boolean tenesPermiso (Permiso permiso) {
    return this.permisos.contains(permiso);
  }
}
