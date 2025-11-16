package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
  // Prueba de que funciona la base con thymeleaf
  @GetMapping("/tessting-base")
  public String home(Model model) {
    model.addAttribute("titulo", "Página de prueba");
    return "hechos/crear_hecho";
  }
}
