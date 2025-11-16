package ar.edu.frba.ddsi.servicio_estadistica.controllers;

import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output.EstadisticaCategoriaOutPutDTO;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output.EstadisticaColeccionOutputDTO;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output.EstadisticaSolicitudDeEliminacionSpamOutPutDTO;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.output.EstadisticaHechosCategoriaOutPutDTO;
import ar.edu.frba.ddsi.servicio_estadistica.services.IEstadisticasExportService;
import ar.edu.frba.ddsi.servicio_estadistica.services.IEstadisticasService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticaController {
    private IEstadisticasService estadisticasService;
    private final IEstadisticasExportService estadisticasExportService;

    public EstadisticaController(IEstadisticasService estadisticasService,
                                 IEstadisticasExportService estadisticasExportService) {
        this.estadisticasService = estadisticasService;
        this.estadisticasExportService = estadisticasExportService;
    }

    @PostMapping("/colecciones")
    public ResponseEntity<List<EstadisticaColeccionOutputDTO>> estadisticaColeccion(@RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(this.estadisticasService.estadisticaColeccion(size));
    }

    @PostMapping("/solicitudesSpam")
    public ResponseEntity<EstadisticaSolicitudDeEliminacionSpamOutPutDTO> estadisticaSolicitudesSpam() {
        return ResponseEntity.ok(this.estadisticasService.estadisticaSolicitudesSpam());
    }

    @PostMapping("/categoria/hechos")
    public ResponseEntity<EstadisticaHechosCategoriaOutPutDTO> estadisticaCategoriaConMasHechos() {
        return ResponseEntity.ok(this.estadisticasService.estadisticaCategoriaConMasHechos());
    }

    @PostMapping("/categorias")
    public ResponseEntity<List<EstadisticaCategoriaOutPutDTO>> estadisticaCategoria(@RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(this.estadisticasService.estadisticaCategoria(size));
    }

    @GetMapping("/exportar/colecciones")
    public void exportarColecciones(HttpServletResponse response) throws IOException {
        estadisticasExportService.exportarColecciones(response);
    }

    @GetMapping("/exportar/categorias")
    public void exportarCategorias(HttpServletResponse response) throws IOException {
        estadisticasExportService.exportarCategorias(response);
    }
}