package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.services;


import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.exceptions.NotFoundException;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.dtos.users.UserRolesPermissionsDTO;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios.Usuario;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.repositories.IUsuariosRepository;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.utils.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LoginService {
    
    private final IUsuariosRepository usuariosRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
    public LoginService(IUsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    public Usuario autenticarUsuario(String username, String password) {
        Optional<Usuario> usuarioOpt = usuariosRepository.findByEmail(username);
        
        if (usuarioOpt.isEmpty()) {
            throw new NotFoundException("Usuario", username);
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // Verificar la contraseña usando BCrypt
        if (!passwordEncoder.matches(password, usuario.getContrasenia())) {
            throw new NotFoundException("Usuario", username);
        }
        
        return usuario;
    }
    
    public String generarAccessToken(String username) {
        return JwtUtil.generarAccessToken(username);
    }
    
    public String generarRefreshToken(String username) {
        return JwtUtil.generarRefreshToken(username);
    }
    
    public UserRolesPermissionsDTO obtenerRolesYPermisosUsuario(String username) {
        Optional<Usuario> usuarioOpt = usuariosRepository.findByEmail(username);
        
        if (usuarioOpt.isEmpty()) {
            throw new NotFoundException("Usuario", username);
        }
        
        Usuario usuario = usuarioOpt.get();
        
        return UserRolesPermissionsDTO.builder()
                .username(usuario.getNombre())
                .rol(usuario.getRol())
                .permisos(usuario.getPermisos())
                .build();
    }
}
