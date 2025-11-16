package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.colecciones.ColeccionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GestionColeccionService {

    private final WebClient webClient;

    @Value("${app.base-url-Agregador}")
    private String agregadorUrl;

    public GestionColeccionService(WebClient.Builder webClientBuilder,
                                  @Value("${app.base-url-Agregador}") String agregadorUrl) {
        this.agregadorUrl = agregadorUrl;
        this.webClient = webClientBuilder.baseUrl(agregadorUrl).build();
    }

    public ColeccionDTO crearColeccion(ColeccionDTO coleccion) {
        return webClient.post()
                .uri("/agregador/colecciones")
                .bodyValue(coleccion)
                .retrieve()
                .bodyToMono(ColeccionDTO.class)
                .block();
    }

    public ColeccionDTO actualizarColeccion(ColeccionDTO coleccion) {
        return webClient.put()
                .uri("/agregador/colecciones/{id}", coleccion.getId())
                .bodyValue(coleccion)
                .retrieve()
                .bodyToMono(ColeccionDTO.class)
                .block();
    }

    public void eliminarColeccion(Long id) {
        webClient.delete()
                .uri("/agregador/colecciones/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void asignarFuente(Long idColeccion, Long idFuente) {
        webClient.post()
                .uri("/agregador/colecciones/{id}/fuente/{idFuente}", idColeccion, idFuente)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
