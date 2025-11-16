package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.RegisterRequestDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.auth.AuthResponseDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.rolesYPermisos.RolesYPermisosDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.usuarios.UsuarioDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.internal.WebApiCallerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.Map;

@Service
public class GestionUsuariosService {
  private static final Logger log = LoggerFactory.getLogger(GestionUsuariosService.class);
  private final WebClient webClient;
  private final WebApiCallerService webApiCallerService;
  private final String authServiceUrl;
  private final String usuariosServiceUrl;

  @Autowired
  public GestionUsuariosService(
      WebApiCallerService webApiCallerService,
      @Value("${auth.service.url}") String authServiceUrl,
      @Value("${usuarios.service.url}") String usuariosServiceUrl) {
    this.webClient = WebClient.builder().build();
    this.webApiCallerService = webApiCallerService;
    this.authServiceUrl = authServiceUrl;
    this.usuariosServiceUrl = usuariosServiceUrl;
  }

  public AuthResponseDTO login(String username, String password) {
    try {
      AuthResponseDTO response = webClient
          .post()
          .uri(authServiceUrl + "/api/auth")
          .bodyValue(Map.of(
              "username", username,
              "password", password
          ))
          .retrieve()
          .bodyToMono(AuthResponseDTO.class)
          .block();
      return response;
    } catch (WebClientResponseException e) {
      log.error(e.getMessage());
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        // Login fallido - credenciales incorrectas
        return null;
      }
      // Otros errores HTTP
      throw new RuntimeException("Error en el servicio de autenticación: " + e.getMessage(), e);
    } catch (Exception e) {
      throw new RuntimeException("Error de conexión con el servicio de autenticación: " + e.getMessage(), e);
    }
  }

  public RolesYPermisosDTO getRolesPermisos(String accessToken) {
    try {
      RolesYPermisosDTO response = webApiCallerService.getWithAuth(
          authServiceUrl + "/api/auth/user/roles-permisos",
          accessToken,
          RolesYPermisosDTO.class
      );
      return response;
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException("Error al obtener roles y permisos: " + e.getMessage(), e);
    }
  }

  public boolean register(RegisterRequestDTO registerRequest) {
    try {
      webClient
          .post()
          .uri(usuariosServiceUrl + "/usuarios")
          .bodyValue(registerRequest)
          .retrieve()
          .bodyToMono(Void.class)
          .block();
      return true;
    } catch (WebClientResponseException e) {
      log.error("Error al registrar usuario: " + e.getMessage());
      if (e.getStatusCode() == HttpStatus.CONFLICT) {
        // Email ya registrado
        return false;
      }
      throw new RuntimeException("Error en el servicio de usuarios: " + e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error de conexión con el servicio de usuarios: " + e.getMessage());
      throw new RuntimeException("Error de conexión con el servicio de usuarios: " + e.getMessage(), e);
    }
  }

  // TODO Modificar para webApiCallerService
  public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
    try {
      UsuarioDTO usuarioCreado = webClient
          .post()
          .uri(usuariosServiceUrl + "/usuarios")
          .bodyValue(usuarioDTO)
          .retrieve()
          .bodyToMono(UsuarioDTO.class)
          .block();
      return usuarioCreado;
    } catch (WebClientResponseException e) {
      log.error("Error al crear usuario: " + e.getMessage());
      if (e.getStatusCode() == HttpStatus.CONFLICT) {
        throw new RuntimeException("El usuario ya existe: " + e.getMessage(), e);
      }
      throw new RuntimeException("Error en el servicio de usuarios: " + e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error de conexión con el servicio de usuarios: " + e.getMessage());
      throw new RuntimeException("Error de conexión con el servicio de usuarios: " + e.getMessage(), e);
    }
  }
}
