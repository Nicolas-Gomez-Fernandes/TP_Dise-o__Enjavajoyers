package ar.edu.frba.ddsi.servicio_agregador.clients;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.external.HechoResponseDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Fuente;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IAPIClient {
  Mono<List<HechoResponseDTO>> getHechos();

  Fuente getFuente();
}
