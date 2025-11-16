package ar.edu.utn.frba.ddsi.metamapa.models.entities.adapters.impl;

import ar.edu.utn.frba.ddsi.metamapa.models.dtos.external.UbicacionRequestDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.adapters.IClimaAdapter;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Ubicacion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ApiGeoref implements IClimaAdapter{
  @Value("${api.georef.url}")
  private String url;
  private WebClient webClient;

  public ApiGeoref() {
    this.webClient = WebClient.builder().baseUrl(this.url).build();
  }

  @Override
  public Ubicacion getUbicacion(Double latitud, Double longitud){

    return webClient.get().uri(uriBuilder -> uriBuilder.path("/ubicacion")
        .queryParam("lat",latitud)
        .queryParam("lon",longitud)
        .queryParam("aplanar", true)
        .queryParam("campos", "estandar")
        .queryParam("formato","json")
        .build())
    .retrieve()
    .bodyToMono(UbicacionRequestDTO.class).map(this::mapToUbicacion).block();
  }

  private Ubicacion mapToUbicacion(UbicacionRequestDTO dto){
    Ubicacion ubicacion = new Ubicacion();
    /*ubicacion.setCiudad(dto.ubicacion.departamento_nombre);
    ubicacion.setProvincia(dto.ubicacion.provincia_nombre);
    ubicacion.setMunicipio(dto.ubicacion.gobierno_local_nombre);*/
    ubicacion.setLatitud(dto.ubicacion.lat);
    ubicacion.setLongitud(dto.ubicacion.lon);
    return ubicacion;
  }
}
