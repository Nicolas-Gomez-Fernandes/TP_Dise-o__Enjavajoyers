package ar.edu.frba.ddsi.servicio_agregador.models.dtos.external.FuenteEstatica;


import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.HechoOutputDTO;
import lombok.Data;

import java.util.List;

@Data
public class HechosResponseDTO {
  List<HechoOutputDTO> hechos;
}
