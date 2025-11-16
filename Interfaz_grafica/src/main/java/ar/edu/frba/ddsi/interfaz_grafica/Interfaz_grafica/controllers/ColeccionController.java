package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.controllers;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.PageColeccionResponseDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.coleccion.ColeccionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.coleccion.PageColeccionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.ColeccionService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/colecciones")
public class ColeccionController {
  private ColeccionService coleccionService;


  public ColeccionController(ColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }

  @GetMapping
  @PreAuthorize("permitAll()")
  public String listarColecciones(
      @RequestParam(defaultValue = "0") Integer page, // valor por defecto = 1
      @RequestParam(defaultValue = "12") Integer size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir,
      Model model) {

    /*// Convertimos a 0-based para Spring Data
    int page0 = page - 1;*/

    /*PageColeccionResponseDTO pageResponse = this.coleccionService.obtenerColeccionesPaginadoPersonalizado(page0, size, sortBy, sortDir);
    pageResponse.setPath("/colecciones");

    model.addAttribute("titulo", "Listado de Colecciones");
    model.addAttribute("colecciones", pageResponse.getContent());
    model.addAttribute("page", pageResponse);*/
    PageColeccionDTO pageResponse = this.coleccionService.obtenerColeccionesPaginado(page, size, sortBy, sortDir);
    model.addAttribute("titulo", "Listado de Colecciones");
    model.addAttribute("colecciones", pageResponse.getContent());
    model.addAttribute("cantHechos", pageResponse.getContent().size());
    model.addAttribute("page", pageResponse);

    return "colecciones/lista";
  }

}
