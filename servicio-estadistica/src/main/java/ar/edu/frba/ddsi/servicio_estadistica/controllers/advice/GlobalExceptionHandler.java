package ar.edu.frba.ddsi.servicio_estadistica.controllers.advice;

import ar.edu.frba.ddsi.servicio_estadistica.exceptions.estadisticas.EstadisticaException;
import ar.edu.frba.ddsi.servicio_estadistica.exceptions.estadisticas.EstadisticaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EstadisticaNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleEstadisticaNotFound(EstadisticaNotFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", "No encontrado");
    body.put("mensaje", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }
}
