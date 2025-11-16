package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.controllers;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.hechos.HechoDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.solicitudEliminacion.SolicitudEliminacionInputDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.exceptions.DuplicateHechoException;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.exceptions.ValidationException;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.HechosService;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores.GestionSolicitudEliminacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/hechos")
public class HechoController {
    private final HechosService hechosService;
    private final GestionSolicitudEliminacionService gestionSolicitudEliminacionService;

    public HechoController(HechosService hechosService, 
                          GestionSolicitudEliminacionService gestionSolicitudEliminacionService) {
        this.hechosService = hechosService;
        this.gestionSolicitudEliminacionService = gestionSolicitudEliminacionService;
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

    @GetMapping("/reportar/{id}")
    public String mostrarFormularioReporte(@PathVariable Long id, Model model) {
        // Obtener el hecho para mostrar su información
        HechoDTO hecho = hechosService.obtenerHechoPorId(id);
        
        model.addAttribute("hecho", hecho);
        model.addAttribute("titulo", "Reportar Hecho");
        return "hechos/solicitud_eliminacion";
    }

    @PostMapping("/reportar")
    @ResponseBody
    public ResponseEntity<?> crearSolicitudEliminacion(@RequestBody SolicitudEliminacionInputDTO solicitud) {
        try {
            gestionSolicitudEliminacionService.crearSolicitud(solicitud);
            return ResponseEntity.ok().body(java.util.Map.of("message", "Solicitud enviada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(java.util.Map.of("message", "Error al crear la solicitud: " + e.getMessage()));
        }
    }

}