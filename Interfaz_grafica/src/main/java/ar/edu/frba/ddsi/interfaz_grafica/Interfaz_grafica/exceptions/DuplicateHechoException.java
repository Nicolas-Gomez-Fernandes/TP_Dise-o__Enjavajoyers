package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.exceptions;

public class DuplicateHechoException extends RuntimeException{


    public DuplicateHechoException(String message) {
        super("El hecho con el titulo" + message + "ya existe");
    }
}
