package ar.edu.frba.ddsi.servicio_agregador.controllers;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.input.SolicitudEliminacionInputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.SolicitudDeEliminacionOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.SolicitudEliminacion;
import ar.edu.frba.ddsi.servicio_agregador.services.ISolicitudDeEliminacionService;
import ar.edu.frba.ddsi.servicio_agregador.services.impl.SolicitudDeEliminacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/agregador/solicitudes-eliminacion")
public class SolicitudDeEliminacionController {
  private final ISolicitudDeEliminacionService solicitudDeEliminacionService;

 public SolicitudDeEliminacionController(SolicitudDeEliminacionService solicitudDeEliminacionService) {
   this.solicitudDeEliminacionService = solicitudDeEliminacionService;
  }

  @PostMapping
  public SolicitudDeEliminacionOutputDTO crearSolicitud(@RequestBody SolicitudEliminacionInputDTO solicitud){
   return solicitudDeEliminacionService.crearSolicitud(solicitud);
  }

  @GetMapping
  public ResponseEntity<List<SolicitudDeEliminacionOutputDTO>> getSolicitudesDeEliminacion() {
  return ResponseEntity.ok(this.solicitudDeEliminacionService.buscarTodos());
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Void> actualizarEstadoDeSolicitud(@PathVariable Long id, @RequestParam boolean aceptado) {
  this.solicitudDeEliminacionService.actualizarEstadoDeSolicitud(id, aceptado);
  return ResponseEntity.noContent().build();
  }
}
