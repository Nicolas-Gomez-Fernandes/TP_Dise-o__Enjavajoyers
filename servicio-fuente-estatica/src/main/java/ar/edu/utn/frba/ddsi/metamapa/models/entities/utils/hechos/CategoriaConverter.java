package ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class CategoriaConverter extends AbstractBeanField<Categoria, String> {

    @Override
    protected Categoria convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return new Categoria(value.trim());
    }
}