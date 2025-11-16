package ar.edu.frba.ddsi.servicio_agregador.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class HechoExistenteException extends RuntimeException {
    public HechoExistenteException(String tituloHecho) {
        super("El hecho con el titulo" + tituloHecho + "ya existe");
    }
}
