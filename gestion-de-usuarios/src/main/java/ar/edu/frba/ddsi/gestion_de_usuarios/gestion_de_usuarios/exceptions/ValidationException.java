package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
    private final Map<String, String> fieldErrors = new HashMap<>();

    public ValidationException(String message) {
        super(message);
    }

    public void addFieldError(String field, String error) {
        fieldErrors.put(field, error);
    }

    public boolean hasFieldErrors() {
        return !fieldErrors.isEmpty();
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}
