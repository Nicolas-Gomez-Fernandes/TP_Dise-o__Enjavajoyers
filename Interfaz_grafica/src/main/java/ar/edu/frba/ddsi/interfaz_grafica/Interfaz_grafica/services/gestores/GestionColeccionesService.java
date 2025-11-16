package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.PageColeccionResponseDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.coleccion.ColeccionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.coleccion.PageColeccionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.internal.WebApiCallerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class GestionColeccionesService {
  private static final Logger log = LoggerFactory.getLogger(GestionColeccionesService.class);
  private final WebClient webClient;
  private static WebApiCallerService webApiCallerService = null;
  private final String authServiceUrl;
  private static String coleccionesAgregadorServiceUrl;



  @Autowired
  public GestionColeccionesService(WebApiCallerService webApiCallerService,
                              @Value("${auth.service.url}") String authServiceUrl,
                              @Value("${agregador.service.url}") String agregadorServiceUrl){
    this.webClient = WebClient.builder().build();
    this.webApiCallerService = webApiCallerService;
    this.authServiceUrl = authServiceUrl;
    this.coleccionesAgregadorServiceUrl = agregadorServiceUrl + "/colecciones";
  }

  public List<ColeccionDTO> obtenerTodasLasColecciones() {
    // llamado normal, sin token
    List<ColeccionDTO> response = webApiCallerService.getListNoAuth(coleccionesAgregadorServiceUrl, ColeccionDTO.class);
    return response != null ? response : List.of();
  }

  public List<ColeccionDTO> obtenerColeccionesDestacadas() {
    // llamado normal, sin token
    List<ColeccionDTO> response = webApiCallerService.getListNoAuth(coleccionesAgregadorServiceUrl + "/destacadas", ColeccionDTO.class);
    return response != null ? response : List.of();
  }

  public PageColeccionResponseDTO obtenerColeccionesPaginadoPersonalizado(Integer page, Integer size, String sortBy, String sortDir) {
    String url = String.format(coleccionesAgregadorServiceUrl + "?page=%d&size=%d&sortBy=%s&sortDir=%s", page, size, sortBy, sortDir);
    PageColeccionResponseDTO response = webApiCallerService.getNoAuth(url, PageColeccionResponseDTO.class);
    return response;
  }

  public PageColeccionDTO obtenerColeccionesPaginado(Integer page, Integer size, String sortBy, String sortDir) {
    String url = String.format(coleccionesAgregadorServiceUrl + "?page=%d&size=%d&sortBy=%s&sortDir=%s", page, size, sortBy, sortDir);
    PageColeccionDTO response = webApiCallerService.getNoAuth( url, PageColeccionDTO.class);
    return response;
  }
}
