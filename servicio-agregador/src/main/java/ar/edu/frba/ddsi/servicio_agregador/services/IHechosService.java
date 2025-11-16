package ar.edu.frba.ddsi.servicio_agregador.services;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.external.HechoResponseDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.HechoOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.fuentes.OrigenFuente;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IHechosService {
  List<Hecho> getHechosByFuentes(List<OrigenFuente> fuentesAsociadas);

  Mono<Void> actualizarYCargarNuevosHechos();

  Hecho getById(Long id);

  void aplicarAlgoritmoDeConsenso();

  List<HechoOutputDTO> getHechos();

  void cargarProvinciasEnHechosSinUbicacion();

  Hecho actualizarDatosHecho(Hecho hechoExistente, HechoResponseDTO dto);

  List<String> getProvinciasDeHechos();
}