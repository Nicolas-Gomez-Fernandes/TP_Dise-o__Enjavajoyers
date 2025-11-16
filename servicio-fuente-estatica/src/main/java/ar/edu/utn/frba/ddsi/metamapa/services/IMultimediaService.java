package ar.edu.utn.frba.ddsi.metamapa.services;

import ar.edu.utn.frba.ddsi.metamapa.models.dtos.output.RtaCargaHechoOutputDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.HechoCSV;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface IMultimediaService {
  RtaCargaHechoOutputDTO cargarDataset(MultipartFile file);
}
