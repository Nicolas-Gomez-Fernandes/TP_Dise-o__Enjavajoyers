package ar.edu.frba.ddsi.servicio_agregador.exceptions;

public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}
