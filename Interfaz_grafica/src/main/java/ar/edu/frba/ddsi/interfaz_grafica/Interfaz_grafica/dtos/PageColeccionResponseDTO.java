package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.coleccion.ColeccionDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

// Sacado de desastres naturales
@EqualsAndHashCode(callSuper = true)
@Data
public class PageColeccionResponseDTO extends BasePageResponseDTO {
  private List<ColeccionDTO> content;
}
