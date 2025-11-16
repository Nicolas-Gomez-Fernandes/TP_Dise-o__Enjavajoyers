package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.colecciones;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.BasePageDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.BasePageResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageColeccionDTO extends BasePageDTO {
  private List<ColeccionDTO> content;
}
