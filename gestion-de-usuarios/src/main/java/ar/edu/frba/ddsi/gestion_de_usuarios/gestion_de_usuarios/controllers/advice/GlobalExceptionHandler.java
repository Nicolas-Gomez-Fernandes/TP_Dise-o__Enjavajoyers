package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.controllers.advice;

import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.exceptions.NotFoundException;
import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<Map<String, Object>> handleUsuarioValidation(ValidationException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Usuario Duplicado");
    body.put("mensaje", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", "No Encontrado");
    body.put("mensaje", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }
}
