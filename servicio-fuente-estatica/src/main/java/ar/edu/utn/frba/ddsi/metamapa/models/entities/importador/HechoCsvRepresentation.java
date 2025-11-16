package ar.edu.utn.frba.ddsi.metamapa.models.entities.importador;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HechoCsvRepresentation {
  @CsvBindByName(column = "Título")
  private String titulo;
  @CsvBindByName(column = "Descripción")
  private String descripcion;
  @CsvBindByName(column = "Categoría")
  private String categoria;
  @CsvBindByName(column = "Latitud")
  private Double latitud;
  @CsvBindByName(column = "Longitud")
  private Double longitud;
  @CsvBindByName(column = "Fecha del hecho")
  private String fecha;
}
