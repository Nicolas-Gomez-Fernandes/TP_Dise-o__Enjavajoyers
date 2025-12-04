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
    private String categoria; // Nombre de la categoría (del servicio agregador)
    private Long id_categoria; // ID de categoría (para crear hechos)
    private Double latitud;
    private Double longitud;
    private MultipartFile files;
    private List<Long> archivos_multimedia;
    private java.time.LocalDateTime fecha_hecho; // Fecha del servicio agregador
    private LocalDate fecha_acontecimiento; // Fecha para crear hechos
    private Long id_contribuyente; // null si es anónimo
    private List<String> etiquetas;
    private Boolean eliminado;
}
