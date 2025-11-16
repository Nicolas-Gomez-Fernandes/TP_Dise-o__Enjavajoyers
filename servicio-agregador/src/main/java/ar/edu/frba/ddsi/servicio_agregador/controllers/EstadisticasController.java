package ar.edu.frba.ddsi.servicio_agregador.controllers;


import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.estadisticas.EstadisticaColeccionOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.estadisticas.EstadisticaSolicitudSpamOutputDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.frba.ddsi.servicio_agregador.services.IEstadisticaService;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.estadisticas.EstadisticaCategoriaOutputDTO;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agregador/estadisticas")
public class EstadisticasController {
    private IEstadisticaService estadisticaService;

    public EstadisticasController(IEstadisticaService estadisticaService) {
        this.estadisticaService = estadisticaService;
    }

    @GetMapping("/categorias")
   public List<EstadisticaCategoriaOutputDTO> estadisticaCategorias(){
        return this.estadisticaService.estadisticaCategorias();
   }

    @GetMapping("/colecciones")
    public List<EstadisticaColeccionOutputDTO> estadisticaColeccion(){
        return this.estadisticaService.estadisticaColeccion();
    }

    @GetMapping("/solicitudesSpam")
    public EstadisticaSolicitudSpamOutputDTO cantidadSolicitudesSpam(){
        return this.estadisticaService.estadisticaSpam();
    }

}
