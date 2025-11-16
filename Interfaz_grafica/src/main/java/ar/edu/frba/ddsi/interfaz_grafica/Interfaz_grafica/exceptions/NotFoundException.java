package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.exceptions;

public class NotFoundException extends RuntimeException {


    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String entidad, Long id) {
        super("No se ha encontrado " + entidad + " de id " + id);
    }

}
