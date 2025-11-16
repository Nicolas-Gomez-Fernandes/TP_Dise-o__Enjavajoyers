package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.solicitudEliminacion.SolicitudEliminacionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class GestionSolicitudEliminacionService {

    private final WebClient webClient;

    @Value("${app.base-url-Agregador}")
    private String agregadorUrl;

    public GestionSolicitudEliminacionService(WebClient.Builder webClientBuilder,
                                             @Value("${app.base-url-Agregador}") String agregadorUrl) {
        this.agregadorUrl = agregadorUrl;
        this.webClient = webClientBuilder.baseUrl(agregadorUrl).build();
    }

    public List<SolicitudEliminacionDTO> obtenerSolicitudesPendientes() {
        return webClient.get()
                .uri("/agregador/solicitudes-eliminacion")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SolicitudEliminacionDTO>>() {})
                .block();
    }

    public void procesarSolicitud(Long id, boolean aceptada) {
        webClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path("/agregador/solicitudes-eliminacion/{id}")
                        .queryParam("aceptado", aceptada)
                        .build(id))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
