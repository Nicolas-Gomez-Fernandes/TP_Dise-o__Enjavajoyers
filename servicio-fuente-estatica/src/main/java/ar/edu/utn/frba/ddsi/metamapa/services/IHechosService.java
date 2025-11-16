package ar.edu.utn.frba.ddsi.metamapa.services;

import ar.edu.utn.frba.ddsi.metamapa.models.dtos.input.HechoInputCsvDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.dtos.output.RtaCargaHechoOutputDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

public interface IHechosService {
  List<HechoOutputDTO> getHechos();
  Hecho crearHecho(HechoInputCsvDTO hecho);
  List<Hecho> eliminarDuplicados(List<Hecho> hechos);
  void guardarHechos(List<Hecho> hechos);
  void eliminar(Long id);
  HechoOutputDTO buscarPorId(Long id);
}