package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.config;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.providers.CustomAuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  public AuthenticationManager authManager(HttpSecurity http, CustomAuthProvider provider) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .authenticationProvider(provider)
        .build();
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            // Recursos estáticos y login público
            .requestMatchers("/login", "/css/**", "/js/**", "/images/**",
                "/", "/metamapa",
                "/hechos/**",
                "/usuarios/register", "/usuarios/crear",
                "/colecciones").permitAll()
            // Ejemplo: Acceso a alumnos: ADMIN y DOCENTE
            //.requestMatchers("/alumnos/**").hasAnyRole("ADMIN", "DOCENTE")
            // Lo demás requiere autenticación
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")    // tu template de login
            .permitAll()
            .defaultSuccessUrl(/*"/perfil"*/ "/metamapa", true) // redirigir tras login exitoso // TODO discutir
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout") // redirigir tras logout
            .permitAll()
        )
        .exceptionHandling(ex -> ex
            // Usuario no autenticado → redirigir a login
            .authenticationEntryPoint((request, response, authException) ->
                response.sendRedirect("/login?unauthorized") // TODO hacer pantalla
            )
            // Usuario autenticado pero sin permisos → redirigir a página de error
            .accessDeniedHandler((request, response, accessDeniedException) ->
                response.sendRedirect("/403") // TODO hacer pantalla
            )
        );

    return http.build();
  }
}
