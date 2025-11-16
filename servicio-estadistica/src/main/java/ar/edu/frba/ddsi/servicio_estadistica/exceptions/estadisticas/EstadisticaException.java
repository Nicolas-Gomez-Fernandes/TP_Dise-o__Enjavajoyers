package ar.edu.frba.ddsi.servicio_estadistica.exceptions.estadisticas;

public class EstadisticaException extends RuntimeException {
  public EstadisticaException(String message) {
    super(message);
  }

  public EstadisticaException(String message, Throwable cause) {
    super(message, cause);
  }
}
