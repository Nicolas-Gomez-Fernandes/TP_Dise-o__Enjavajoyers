package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.services;

import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.exceptions.DuplicateEmailException;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.exceptions.NotFoundException;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.exceptions.ValidationException;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.dtos.UsuarioInputDTO;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.dtos.UsuarioOutputDTO;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios.Permiso;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios.TipoRol;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios.Usuario;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.repositories.IUsuariosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UsuarioService {
  private final IUsuariosRepository usuariosRepository;

  // TODO permisos?
  private List<Permiso> permisosContribuyente = List.of(
      Permiso.VER_COLECCIONES, Permiso.VER_HECHOS,
      Permiso.EDITAR_HECHO,
      Permiso.ELIMINAR_USUARIO
  );

  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public UsuarioService(IUsuariosRepository usuariosRepository) {
    this.usuariosRepository = usuariosRepository;
  }

  public List<Usuario> listarUsuarios() {
    return usuariosRepository.findAll();
  }

  public UsuarioOutputDTO crearUsuario(UsuarioInputDTO usuario) {
    try {
      this.validarDatosBasicos(usuario);
      this.validarDatosDuplicados(usuario);

      Usuario usuarioEntity = this.convertirADominio(usuario);

      return this.convertirADTO(usuariosRepository.save(usuarioEntity));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return null;
  }


  public void validarDatosBasicos(UsuarioInputDTO usuarioInput) {
    if (usuarioInput.getNombre() == null || usuarioInput.getNombre().trim().isEmpty()) {
      throw new ValidationException("El nombre no puede estar vacío");
    }
    // ver que el rol exista en los enums
    if (usuarioInput.getRol() == null || !List.of(TipoRol.values()).contains(usuarioInput.getRol())) {
      throw new ValidationException("El rol es inválido");
    }
  }

  public void validarDatosDuplicados(UsuarioInputDTO usuarioInput) {
    if (this.usuariosRepository.findByEmail(usuarioInput.getEmail()).isPresent()) {
      throw new DuplicateEmailException(usuarioInput.getEmail());
    }

  }

  private UsuarioOutputDTO convertirADTO(Usuario usuario) {
    return UsuarioOutputDTO.builder()
        .id(usuario.getId())
        .nombre(usuario.getNombre())
        .apellido(usuario.getApellido())
        .email(usuario.getEmail())
        .rol(usuario.getRol())
        .permisos(usuario.getPermisos())
        .build();
  }

  private Usuario convertirADominio(UsuarioInputDTO usuarioInput) {
    return Usuario.builder()
        .nombre(usuarioInput.getNombre())
        .apellido(usuarioInput.getApellido())
        .email(usuarioInput.getEmail())
        .contrasenia(encoder.encode(usuarioInput.getContrasenia()))
        .rol(usuarioInput.getRol())
        .build();
  }

  public void eliminarUsuario(Long id) {
    usuariosRepository.deleteById(id);
  }

  public UsuarioOutputDTO actualizarUsuario(Long id, UsuarioInputDTO usuario) {
    Usuario usuarioEntity = intentarRecuperarUsuario(id);

    this.validarDatosBasicos(usuario);

    //si el mail cambio que no exista otro con ese mail
    if(!Objects.equals(usuario.getEmail(), usuarioEntity.getEmail())){
      this.validarDatosDuplicados(usuario);
    }

    usuarioEntity.setEmail(usuario.getEmail());
    //usuarioEntity.setContrasenia(usuario.getContrasenia());//TODO ver esto para que con otra instancia se cambie la contraseña
    if (usuario.getContrasenia() != null && !usuario.getContrasenia().isEmpty()) {
      usuarioEntity.setContrasenia(encoder.encode(usuario.getContrasenia()));
    }
    usuarioEntity.setNombre(usuario.getNombre());
    usuarioEntity.setApellido(usuario.getApellido());


    return this.convertirADTO(usuariosRepository.save(usuarioEntity));
  }

  public Usuario intentarRecuperarUsuario(Long id){
    return this.usuariosRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("usuario", id.toString()));
  }

  public UsuarioOutputDTO obtenerUsuarioPorId(Long id) {
    Usuario usuario = this.usuariosRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("usuario", id.toString()));

    return this.convertirADTO(usuario);
  }

}
