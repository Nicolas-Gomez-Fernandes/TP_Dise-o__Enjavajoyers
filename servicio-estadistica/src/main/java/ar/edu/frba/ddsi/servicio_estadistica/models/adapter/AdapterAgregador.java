package ar.edu.frba.ddsi.servicio_estadistica.models.adapter;

import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.ResponseCategoria;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.ResponseColeccion;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.ResponseSpam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Component
public class AdapterAgregador implements IAdapterAgregador {
    private WebClient webClient;

    public AdapterAgregador(WebClient.Builder webClientBuilder, @Value("${app.base-url-fuenteAgregador}") String urlAgregador) {
        this.webClient = webClientBuilder.baseUrl(urlAgregador).build();
    }

    @Override
    public List<ResponseColeccion> topProvinciaConMasHechosDeColecciones() {
        return webClient.get().uri("/estadisticas/colecciones")
            .retrieve()
            .bodyToFlux(ResponseColeccion.class)
            .collectList()
            .block();
    }

    @Override
    public List<ResponseCategoria> estadisticaCategoria() {
        return webClient.get().uri("/estadisticas/categorias")
            .retrieve()
            .bodyToFlux(ResponseCategoria.class)
            .collectList()
            .block();
    }


//    @Override
//    public List<ResponseCategoria> topCategoriaConMasHechos() {
//        return webClient.get()
//            .uri("/estadisticas/categoria")
//            .retrieve()
//            .bodyToFlux(ResponseCategoria.class)
//            .collectList()
//            .block();
//    }
////
//    @Override
//    public List<ResponseCategoria> topProvinciaConMasHechosDeCategorias() {
//        return webClient.get()
//            .uri("/estadisticas/provincia")
//            .retrieve()
//            .bodyToFlux(ResponseCategoria.class)
//            .collectList()
//            .block();
//    }
//
//    @Override
//    public List<ResponseCategoria> topHoraConMasHechos() {
//        return webClient.get()
//            .uri("/estadisticas/hora")
//            .retrieve()
//            .bodyToFlux(ResponseCategoria.class)
//            .collectList()
//            .block();
//    }

    @Override
    public ResponseSpam cantidadDeSolicitudesSpam() {
        return webClient.get().uri("/estadisticas/solicitudesSpam")
            .retrieve()
            .bodyToMono(ResponseSpam.class)
            .block();
    }
}
