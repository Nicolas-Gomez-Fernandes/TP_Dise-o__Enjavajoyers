package ar.edu.frba.ddsi.servicio_agregador.clients.impl;

import ar.edu.frba.ddsi.servicio_agregador.clients.IAPIClient;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.external.HechoResponseDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.mapper.ManualHechoMapper;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Fuente;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FuenteProxyClient implements IAPIClient {
  private WebClient webClient;

  @Value("${app.base-url-FuenteProxy}")
  private String urlProxy;
  @Getter
  private Fuente fuente;


  public FuenteProxyClient(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("http://localhost:8082/proxy").build();
    this.fuente = Fuente.PROXY;
  }

  public Mono<List<HechoResponseDTO>> getHechos() {
    return webClient.get().uri("/hechos")
        .retrieve()
        .bodyToFlux(HechoResponseDTO.class)
        .collectList();
  }

  public List<Hecho> getHechosMetamapa() {
    return webClient.get().uri("/hechos")
        .retrieve()
        .bodyToFlux(HechoResponseDTO.class)
        .map(ManualHechoMapper::map)
        .collectList()
        .block();
  }
}
