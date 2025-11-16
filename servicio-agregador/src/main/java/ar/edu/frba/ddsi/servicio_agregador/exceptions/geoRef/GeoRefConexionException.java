package ar.edu.frba.ddsi.servicio_agregador.exceptions.geoRef;

public class GeoRefConexionException extends RuntimeException {
  public GeoRefConexionException(String message) {
    super(message);
  }

  public GeoRefConexionException(Throwable cause) {
    super("Fallo en la comunicación con GeoRef", cause);
  }
}
