package ar.edu.frba.ddsi.servicio_agregador.clients.impl;

import ar.edu.frba.ddsi.servicio_agregador.clients.IAPIClient;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.external.HechoResponseDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Fuente;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FuenteEstaticaClient implements IAPIClient {
  private WebClient webClient;
  //@Value("${app.base-url-FuenteEstatica}")
  //private String urlEstatica;
  @Getter
  @Setter
  private Fuente fuente;

  public FuenteEstaticaClient(WebClient.Builder webClientBuilder, @Value("${app.base-url-FuenteEstatica}") String urlEstatica) {
    this.webClient = webClientBuilder.baseUrl(urlEstatica).build();
    this.fuente = Fuente.ESTATICA;
  }

  public Mono<List<HechoResponseDTO>> getHechos() {
    return webClient.get().uri("/hechos")
        .retrieve()
        .bodyToFlux(HechoResponseDTO.class)
        .collectList();
  }

  public Boolean isFuente(Fuente fuente) {
    return fuente.equals(this.fuente);
  }

}
