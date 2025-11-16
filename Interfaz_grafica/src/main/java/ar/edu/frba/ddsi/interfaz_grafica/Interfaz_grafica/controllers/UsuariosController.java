package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.controllers;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.RegisterRequestDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.rolesYPermisos.Rol;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.usuarios.UsuarioDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.UsuariosService;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores.GestionUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    private final UsuariosService usuariosService;

    @Autowired
    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping("/register")
    public String crearUsuario(Model model) {
        model.addAttribute("titulo", "Registro de Usuario");
        model.addAttribute("usuario", new UsuarioDTO(Rol.CONTRIBUYENTE));
        return "register";
    }

    @PostMapping("/crear")
    public String crearUsuario(@ModelAttribute("usuario") UsuarioDTO usuarioDTO,
                                @RequestParam("confirmPassword") String confirmPassword,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            UsuarioDTO usuarioCreado = usuariosService.crearUsuario(usuarioDTO);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario creado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            // return "redirect:/usuarios/" + usuarioCreado.getId(); //TODO
            return "/";
        }
        catch (Exception ex) {
            model.addAttribute("mensaje", ex.getMessage());
            model.addAttribute("tipoMensaje", "danger");
            model.addAttribute("titulo", "Registro de Usuario");
            return "register";
        }
    }
}
