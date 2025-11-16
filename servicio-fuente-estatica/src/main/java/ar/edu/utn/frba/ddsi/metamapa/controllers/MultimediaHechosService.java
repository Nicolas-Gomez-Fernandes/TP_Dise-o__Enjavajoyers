package ar.edu.utn.frba.ddsi.metamapa.controllers;

import ar.edu.utn.frba.ddsi.metamapa.models.dtos.output.RtaCargaHechoOutputDTO;
import ar.edu.utn.frba.ddsi.metamapa.services.IMultimediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/estatica/multimedia/hechos")
public class MultimediaHechosService {
  private IMultimediaService multimediaService;

  public MultimediaHechosService(IMultimediaService multimediaService){
    this.multimediaService = multimediaService;
  }

  @PostMapping("/upload")
  public ResponseEntity<RtaCargaHechoOutputDTO> cargarDataset(@RequestPart("file") MultipartFile file) {
    return ResponseEntity.ok(this.multimediaService.cargarDataset(file));
  }
}
