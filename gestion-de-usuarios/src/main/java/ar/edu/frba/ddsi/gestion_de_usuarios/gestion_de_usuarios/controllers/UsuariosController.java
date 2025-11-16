package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.controllers;


import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.dtos.UsuarioInputDTO;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.dtos.UsuarioOutputDTO;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios.Permiso;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios.Usuario;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
  private UsuarioService usuarioService;

  public UsuariosController(UsuarioService usuariosService) {
    this.usuarioService = usuariosService;
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping
  public ResponseEntity<List<Usuario>> listarUsuarios() {
    return ResponseEntity.ok(this.usuarioService.listarUsuarios());
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<UsuarioOutputDTO> obtenerUsuarioPorId(@PathVariable Long id) {
    return ResponseEntity.ok(this.usuarioService.obtenerUsuarioPorId(id));
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @PostMapping
  public ResponseEntity<UsuarioOutputDTO> crearUsuario(@RequestBody UsuarioInputDTO usuario) {
    return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
  }

  @RequestMapping("/permisos")
  public ResponseEntity<List<Permiso>> allPermisos() {
    return ResponseEntity.ok(List.of(Permiso.values()));
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarUsuario(@RequestBody Long id) {
    this.usuarioService.eliminarUsuario(id);
    return ResponseEntity.noContent().build();
  }


  @PutMapping("/{id}")
  public ResponseEntity<UsuarioOutputDTO> actualizarUsuario(@RequestBody Long id, @RequestBody UsuarioInputDTO usuario) {
    return ResponseEntity.ok(this.usuarioService.actualizarUsuario(id,usuario));
  }

}

