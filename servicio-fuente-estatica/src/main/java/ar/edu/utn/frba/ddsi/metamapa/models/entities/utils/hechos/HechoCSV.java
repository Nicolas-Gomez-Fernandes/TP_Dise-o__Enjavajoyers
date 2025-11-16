package ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.CategoriaConverter;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class HechoCSV{

    @CsvBindByPosition(position = 0)
    private String titulo;

    @CsvBindByPosition(position = 1)
    private String descripcion;

    @CsvCustomBindByPosition(position = 2, converter = CategoriaConverter.class)
    private Categoria categoria;

    @CsvBindByPosition(position = 3)
    private Double latitud;

    @CsvBindByPosition(position = 4)
    private Double longitud;

    @CsvCustomBindByPosition(position = 5, converter = FechaConverter.class)
    private LocalDateTime fecha;

}
