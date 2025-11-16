package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;


/*@Entity
@Table(name = "roles")
@Data
public class Rol{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "tipo", nullable = false)
  private TipoRol tipo;
  @ManyToMany
  @JoinTable(
      name = "permisos_roles",
      joinColumns = @JoinColumn(name = "rol_id"),
      inverseJoinColumns = @JoinColumn(name = "permiso_id")
  )
  private List<Permiso> permisos;

  public Rol() {
    this.permisos = new ArrayList<>();
  }

  public void agregarPermiso (Permiso permiso) {
    if (!this.permisos.contains(permiso)) {
      this.permisos.add(permiso);
    }
  }

  public boolean tenesPermiso (Permiso permiso) {
    return this.permisos.contains(permiso);
  }

}*/

