package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.exceptions;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {

      super("El Email " + email + " ya existe");
    }
}
