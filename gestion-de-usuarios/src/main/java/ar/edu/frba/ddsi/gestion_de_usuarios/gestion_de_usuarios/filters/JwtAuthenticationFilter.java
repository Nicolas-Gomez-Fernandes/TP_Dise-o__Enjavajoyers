package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.filters;


import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    System.out.println("=== JWT FILTER DEBUG ===");
    System.out.println("URI: " + request.getRequestURI());
    String header = request.getHeader("Authorization");
    System.out.println("Authorization header: " + (header != null ? "Sí (" + header.length() + " chars)" : "No"));
    
    // si el header no es nulo y empieza con "Bearer ", extraer el token
    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);
      System.out.println("Token extraído: " + token.substring(0, Math.min(20, token.length())) + "...");
      try {
        // TODO -- ir al repo, fijarse los roles del usuario y setearlos acá
        String username = JwtUtil.validarToken(token);
        System.out.println("Token válido para usuario: " + username);
        var authorities = java.util.Arrays.asList(
            new SimpleGrantedAuthority("ROLE_USER"),
            new SimpleGrantedAuthority("ROLE_CONTRIBUYENTE"),
            new SimpleGrantedAuthority("ROLE_ADMIN")
        );
        var auth = new UsernamePasswordAuthenticationToken(
            username,
            null,
            authorities
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println("Autenticación establecida con roles: " + authorities);
      } catch (Exception e) {
        System.out.println("Error validando token: " + e.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
        return;
      }
    } else {
      System.out.println("No hay token de autorización");
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    // No aplicar el filtro JWT solo a los endpoints públicos de autenticación y registro
    return path.equals("/api/auth") || path.equals("/api/auth/refresh") || 
           path.equals("/usuarios/register") || path.equals("/usuarios/permisos");
  }
}
