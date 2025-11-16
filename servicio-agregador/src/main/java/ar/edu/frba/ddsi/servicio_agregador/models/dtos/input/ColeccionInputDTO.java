package ar.edu.frba.ddsi.servicio_agregador.models.dtos.input;


import ar.edu.frba.ddsi.servicio_agregador.models.entities.Usuario;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.TipoAlgoritmoDeConsenso;
import lombok.Data;

import java.util.List;

@Data
public class ColeccionInputDTO {
  private String titulo;
  private String descripcion;
  private List<CriterioPertenenciaInputDTO> criterios;
  private List<Long> fuentesAsociadas; //Long porque conocemos ya a las fuentes guardadas en nuestra DB y en el front mostramos el noombre que esta asociado a un id
  private Long id_creador;
  private TipoAlgoritmoDeConsenso algoritmoDeConsenso; // puede venir en null
}