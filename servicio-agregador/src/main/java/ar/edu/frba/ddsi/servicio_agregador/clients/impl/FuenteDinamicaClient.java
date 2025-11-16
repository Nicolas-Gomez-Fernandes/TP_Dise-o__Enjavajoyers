package ar.edu.frba.ddsi.servicio_agregador.clients.impl;

import ar.edu.frba.ddsi.servicio_agregador.clients.IAPIClient;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.external.HechoResponseDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Fuente;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FuenteDinamicaClient implements IAPIClient  {
  private WebClient webClient;
  @Getter
  @Setter
  //@Value("${app.base-url-FuenteDinamica}")
  //private String urlDinamica;
  private Fuente fuente;

  public FuenteDinamicaClient(WebClient.Builder webClientBuilder, @Value("${app.base-url-FuenteDinamica}") String urlDinamica ) {
    this.webClient = webClientBuilder.baseUrl(urlDinamica).build();
    this.fuente = Fuente.DINAMICA;
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
