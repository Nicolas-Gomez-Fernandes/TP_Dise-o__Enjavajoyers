package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.solicitudEliminacion.SolicitudEliminacionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.solicitudEliminacion.SolicitudEliminacionInputDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    public SolicitudEliminacionDTO crearSolicitud(SolicitudEliminacionInputDTO solicitud) {
        return webClient.post()
                .uri("/agregador/solicitudes-eliminacion")
                .body(Mono.just(solicitud), SolicitudEliminacionInputDTO.class)
                .retrieve()
                .bodyToMono(SolicitudEliminacionDTO.class)
                .block();
    }

    public List<SolicitudEliminacionDTO> obtenerSolicitudesPendientes() {
        return webClient.get()
                .uri("/agregador/solicitudes-eliminacion")
                .retrieve()
                .bodyToFlux(SolicitudEliminacionDTO.class)
                .collectList()
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
