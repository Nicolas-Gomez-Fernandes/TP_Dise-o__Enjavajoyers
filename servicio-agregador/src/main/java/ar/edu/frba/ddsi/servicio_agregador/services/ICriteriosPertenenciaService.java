package ar.edu.frba.ddsi.servicio_agregador.services;
import java.util.List;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.input.CriterioPertenenciaInputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.CriteriosPertenenciaOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.ICriterioPertenencia;

public interface ICriteriosPertenenciaService {
    ICriterioPertenencia crearCriterio(CriterioPertenenciaInputDTO criterioPertenenciaInputDTO);
    CriteriosPertenenciaOutputDTO buscarPorId(Long id);
    List<CriteriosPertenenciaOutputDTO> buscarTodos();
}
