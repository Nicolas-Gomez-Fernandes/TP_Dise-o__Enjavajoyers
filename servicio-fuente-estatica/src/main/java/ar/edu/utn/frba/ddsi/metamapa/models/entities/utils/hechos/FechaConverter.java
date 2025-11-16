package ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos;


import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FechaConverter extends AbstractBeanField {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    protected LocalDate convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException
    {
        return LocalDate.parse(s, FORMATTER);
    }
}