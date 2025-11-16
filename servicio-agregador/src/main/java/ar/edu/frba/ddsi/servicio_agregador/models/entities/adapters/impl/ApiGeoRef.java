package ar.edu.frba.ddsi.servicio_agregador.models.entities.adapters.impl;

import ar.edu.frba.ddsi.servicio_agregador.exceptions.geoRef.GeoRefConexionException;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.ubiApiGeoRef.UbicacionGeoRefDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.adapters.IApiGeoRef;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class ApiGeoRef implements IApiGeoRef {
    private WebClient webClient;

    public ApiGeoRef(@Value("${app.base-url-ApiGeoRef}") String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public UbicacionGeoRefDTO getProvincia(Double latitud, Double longitud) {
      try {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/ubicacion")
                .queryParam("lat", latitud)
                .queryParam("lon", longitud)
                .build())
            .retrieve()
            .bodyToMono(UbicacionGeoRefDTO.class)
            .block();
      } catch (Exception e) {
        throw new GeoRefConexionException(e);
      }
    }


}
