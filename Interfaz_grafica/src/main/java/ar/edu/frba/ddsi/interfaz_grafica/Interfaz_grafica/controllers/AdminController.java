package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.controllers;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.colecciones.ColeccionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.solicitudEliminacion.SolicitudEliminacionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.AdminService;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.ColeccionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    private final AdminService adminService;
    private final ColeccionService coleccionService;

    public AdminController(AdminService adminService, ColeccionService coleccionService) {
        this.adminService = adminService;
        this.coleccionService = coleccionService;
    }

    // ========== PANEL PRINCIPAL ==========
    @GetMapping("/panel")
    public String panelAdmin(Model model) {
        model.addAttribute("titulo", "Panel de Administración");
        return "admin/panel";
    }

    // ========== GESTIÓN DE COLECCIONES ==========
    
    @GetMapping("/colecciones")
    public String gestionColecciones(Model model) {
        List<ColeccionDTO> colecciones = coleccionService.obtenerTodasLasColecciones();
        model.addAttribute("titulo", "Gestión de Colecciones");
        model.addAttribute("colecciones", colecciones);
        return "admin/colecciones/lista";
    }

    @GetMapping("/colecciones/nueva")
    public String formularioNuevaColeccion(Model model) {
        model.addAttribute("titulo", "Nueva Colección");
        model.addAttribute("coleccion", new ColeccionDTO());
        model.addAttribute("esNueva", true);
        return "admin/colecciones/form";
    }

    @PostMapping("/colecciones/crear")
    public String crearColeccion(@ModelAttribute ColeccionDTO coleccion, 
                                RedirectAttributes redirectAttributes) {
        try {
            ColeccionDTO nuevaColeccion = adminService.crearColeccion(coleccion);
            redirectAttributes.addFlashAttribute("mensaje", "Colección creada exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/admin/colecciones";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al crear la colección: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/admin/colecciones/nueva";
        }
    }

    @GetMapping("/colecciones/{id}/editar")
    public String formularioEditarColeccion(@PathVariable Long id, Model model, 
                                           RedirectAttributes redirectAttributes) {
        try {
            ColeccionDTO coleccion = coleccionService.obtenerColeccionPorId(id);
            model.addAttribute("titulo", "Editar Colección");
            model.addAttribute("coleccion", coleccion);
            model.addAttribute("esNueva", false);
            return "admin/colecciones/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al cargar la colección: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/admin/colecciones";
        }
    }

    @PostMapping("/colecciones/{id}/actualizar")
    public String actualizarColeccion(@PathVariable Long id, 
                                     @ModelAttribute ColeccionDTO coleccion,
                                     RedirectAttributes redirectAttributes) {
        try {
            coleccion.setId(id);
            adminService.actualizarColeccion(coleccion);
            redirectAttributes.addFlashAttribute("mensaje", "Colección actualizada exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/admin/colecciones";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar la colección: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/admin/colecciones/" + id + "/editar";
        }
    }

    @PostMapping("/colecciones/{id}/eliminar")
    public String eliminarColeccion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.eliminarColeccion(id);
            redirectAttributes.addFlashAttribute("mensaje", "Colección eliminada exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar la colección: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/colecciones";
    }

    @PostMapping("/colecciones/{idColeccion}/asignar-fuente")
    public String asignarFuenteEstatica(@PathVariable Long idColeccion,
                                       RedirectAttributes redirectAttributes) {
        try {
            // ID de la fuente estática (asumiendo que es 1, ajustar según tu BD)
            Long idFuenteEstatica = 1L;
            adminService.asignarFuenteAColeccion(idColeccion, idFuenteEstatica);
            redirectAttributes.addFlashAttribute("mensaje", "Fuente Estática asignada exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al asignar fuente: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/colecciones";
    }

    // ========== GESTIÓN DE SOLICITUDES DE ELIMINACIÓN ==========
    
    @GetMapping("/solicitudes-eliminacion")
    public String gestionSolicitudes(Model model) {
        List<SolicitudEliminacionDTO> solicitudes = adminService.obtenerSolicitudesPendientes();
        model.addAttribute("titulo", "Gestión de Solicitudes de Eliminación");
        model.addAttribute("solicitudes", solicitudes);
        return "admin/solicitudes/lista";
    }

    @PostMapping("/solicitudes-eliminacion/{id}/aceptar")
    public String aceptarSolicitud(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.procesarSolicitud(id, true);
            redirectAttributes.addFlashAttribute("mensaje", "Solicitud aceptada. El hecho ha sido eliminado.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al procesar la solicitud: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/solicitudes-eliminacion";
    }

    @PostMapping("/solicitudes-eliminacion/{id}/rechazar")
    public String rechazarSolicitud(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.procesarSolicitud(id, false);
            redirectAttributes.addFlashAttribute("mensaje", "Solicitud rechazada.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "info");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al procesar la solicitud: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/solicitudes-eliminacion";
    }
}
