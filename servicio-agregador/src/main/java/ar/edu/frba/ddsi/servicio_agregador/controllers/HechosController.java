package ar.edu.frba.ddsi.servicio_agregador.controllers;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.HechoOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.services.impl.HechosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agregador/hechos")
public class HechosController {
    private HechosService hechosService;

    public HechosController(HechosService hechosService) {
        this.hechosService = hechosService;
    }

    //temporal por ahi conviene con seederService
    @GetMapping("/inicializar")
    public boolean inicializarDatos(){
        this.hechosService.actualizarYCargarNuevosHechos();
        return true;
    }

    @GetMapping
    public List<HechoOutputDTO> getHechos(){
        return this.hechosService.getHechos();
    }

    @GetMapping("/provincias")
    public ResponseEntity<List<String>> getProvincias(){
        return ResponseEntity.ok(this.hechosService.getProvinciasDeHechos());
    }

    /*
    @GetMapping()
    public List<HechoOutputDTO> getHechos(
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) LocalDateTime fechaReporteDesde,
        @RequestParam(required = false) LocalDateTime fechaReporteHasta,
        @RequestParam(required = false) LocalDateTime fechaAcotencimientoDesde,
        @RequestParam(required = false) LocalDateTime fechaAcotencimientoHasta,
        @RequestParam(required = false) Ubicacion ubicacion
    ){
        return this.hechosService.getHechos(new FiltroHecho(categoria, fechaReporteDesde, fechaReporteHasta, fechaAcotencimientoDesde, fechaAcotencimientoHasta, ubicacion));
    }
    */



}
