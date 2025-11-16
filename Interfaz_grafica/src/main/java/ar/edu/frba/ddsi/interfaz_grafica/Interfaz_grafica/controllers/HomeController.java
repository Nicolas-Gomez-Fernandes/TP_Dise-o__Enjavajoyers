package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.controllers;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.coleccion.ColeccionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.ColeccionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
  private ColeccionService coleccionService;

  public HomeController(ColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }

  @GetMapping("/metamapa")
  public String home(Model model) {
    List<ColeccionDTO> coleccionesDestacadas = this.coleccionService.obtenerColeccionesDestacadas();
    Boolean tieneImagen = coleccionesDestacadas.stream().anyMatch(c -> c.getImagen_url() != null && !c.getImagen_url().isEmpty());
    model.addAttribute("titulo", "MetaMapa");
    model.addAttribute("coleccionesDestacadas", coleccionesDestacadas);
    model.addAttribute("tieneImagen", tieneImagen);

    return "landing/landing";
  }

  @GetMapping("/")
  public String landing() {
    return "redirect:/metamapa";
  }

}
