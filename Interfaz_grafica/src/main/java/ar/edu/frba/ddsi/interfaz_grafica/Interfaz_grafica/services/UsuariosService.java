package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.usuarios.UsuarioDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores.GestionUsuariosService;
import org.springframework.stereotype.Service;

@Service
public class UsuariosService {

  private GestionUsuariosService gestionUsuariosService;

  public UsuariosService(GestionUsuariosService gestionUsuariosService) {
    this.gestionUsuariosService = gestionUsuariosService;
  }

  public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {

    this.validarUsuario(usuarioDTO);

    return gestionUsuariosService.crearUsuario(usuarioDTO);
  }

  private void validarUsuario(UsuarioDTO usuarioDTO) {
    // TODO: Validaciones
  }
}
