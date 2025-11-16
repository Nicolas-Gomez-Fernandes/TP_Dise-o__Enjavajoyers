package ar.edu.utn.frba.ddsi.metamapa.controllers;

import ar.edu.utn.frba.ddsi.metamapa.models.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.dtos.output.RtaCargaHechoOutputDTO;
import ar.edu.utn.frba.ddsi.metamapa.services.IHechosService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/estatica/hechos")
public class HechosController {
  private IHechosService hechosService;

  public HechosController(IHechosService hechosService){
    this.hechosService = hechosService;
    //this.hechosService.cargarDesdeDataset("C:\\Users\\Usuario\\OneDrive - UTN.BA\\Materias 3er Nivel\\DDS-Diseño de Sistemas\\TPA-MetaMapa\\Fuentes de hechos\\casoDePruebaE1.csv");
  }

  @GetMapping
  public ResponseEntity<List<HechoOutputDTO>> getHechos() {
    return ResponseEntity.ok(hechosService.getHechos());
  }

  /*@PostMapping("/import-dataset")
  public void cargarDataset(@RequestBody String path){
    this.hechosService.cargarDesdeDataset(path);
  }*/

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteHecho(@PathVariable("id") Long id) {
    this.hechosService.eliminar(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<HechoOutputDTO> getHechoById(@PathVariable ("id") Long id){
    return ResponseEntity.ok(this.hechosService.buscarPorId(id));
  }


}


