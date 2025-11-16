package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.hechos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.List;

@Data
public class HechoDTO {
    private Long id; // null si es nuevo
    private String titulo;
    private String descripcion;
    private Long id_categoria;
    private Double latitud;
    private MultipartFile files;
    private List<Long> archivos_multimedia;
    private LocalDate fecha_acontecimiento;
    private Long id_contribuyente; // null si es anónimo
    private List<String> etiquetas;
}
