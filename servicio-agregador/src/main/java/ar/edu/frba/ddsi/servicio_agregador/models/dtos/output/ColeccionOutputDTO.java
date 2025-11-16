package ar.edu.frba.ddsi.servicio_agregador.models.dtos.output;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.fuentes.OrigenFuente;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ColeccionOutputDTO {
  private String id;
  private String titulo;
  private String descripcion;
  private List<OrigenFuente> fuentes;
  //private List<HechoOutputDTO> hechos;
}
