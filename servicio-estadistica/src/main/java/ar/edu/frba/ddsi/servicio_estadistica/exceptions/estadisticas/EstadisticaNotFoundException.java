package ar.edu.frba.ddsi.servicio_estadistica.exceptions.estadisticas;

public class EstadisticaNotFoundException extends EstadisticaException {
  public EstadisticaNotFoundException(String message) {
    super(message);
  }

  public EstadisticaNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
