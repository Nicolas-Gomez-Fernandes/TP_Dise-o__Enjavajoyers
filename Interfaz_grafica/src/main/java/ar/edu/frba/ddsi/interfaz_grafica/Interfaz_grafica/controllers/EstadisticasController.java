package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequestMapping("/estadisticas")
public class EstadisticasController {

    private final WebClient webClient;

    @Value("${app.base-url-Estadistica:http://localhost:8084}")
    private String estadisticaServiceUrl;

    public EstadisticasController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping
    public String mostrarEstadisticas(Model model) {
        model.addAttribute("titulo", "Estadísticas de MetaMapa");
        return "estadisticas/index";
    }

    @GetMapping("/api/categorias")
    @ResponseBody
    public ResponseEntity<?> obtenerEstadisticasCategorias() {
        try {
            Object estadisticas = webClient.get()
                    .uri(estadisticaServiceUrl + "/estadisticas/categorias?size=100")
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(java.util.Map.of("error", "Error al obtener estadísticas de categorías: " + e.getMessage()));
        }
    }

    @GetMapping("/api/colecciones")
    @ResponseBody
    public ResponseEntity<?> obtenerEstadisticasColecciones() {
        try {
            Object estadisticas = webClient.get()
                    .uri(estadisticaServiceUrl + "/estadisticas/colecciones?size=100")
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(java.util.Map.of("error", "Error al obtener estadísticas de colecciones: " + e.getMessage()));
        }
    }
}
