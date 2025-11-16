package ar.edu.utn.frba.ddsi.metamapa.models.entities.importador;

import ar.edu.utn.frba.ddsi.metamapa.models.dtos.input.HechoInputCsvDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TipoImportador {
  Mono<List<Hecho>> importarHechosDesdePath(String path);
  List<HechoInputCsvDTO> importarDatasetMultiPartFile(MultipartFile filePath);
}
