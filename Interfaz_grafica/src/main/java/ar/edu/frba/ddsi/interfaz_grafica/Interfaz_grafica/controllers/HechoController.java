package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.controllers;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.HechoDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.exceptions.DuplicateHechoException;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.exceptions.ValidationException;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.HechosService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/hechos")
public class HechoController {
    private final HechosService hechosService;

    public HechoController(HechosService hechosService) {
        this.hechosService = hechosService;
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarHecho(@ModelAttribute("id") Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            hechosService.eliminarHecho(id);
            redirectAttributes.addFlashAttribute("mensaje", "Hecho eliminado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/hechos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar el hecho: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/hechos";
        }
    }

    @GetMapping("/detalle/{id}")
    public String verDetalleHecho(@PathVariable Long id, Model model) {
        // Llamás al servicio para obtener el hecho
        HechoDTO hecho = hechosService.obtenerHechoPorId(id);

        model.addAttribute("hecho", hecho);
        model.addAttribute("titulo", "Detalle del Hecho");
        model.addAttribute("contenido", "hechos/detalle-hecho");
        return "hechos/detalle-hecho";
    }

}