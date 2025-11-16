package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String entidad, String id) {
        super("No se ha encontrado " + entidad + " de id " + id);
    }
}
