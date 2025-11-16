package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.HechoDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.exceptions.NotFoundException;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.internal.WebApiCallerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class GestionHechosService {
    private static final Logger log = LoggerFactory.getLogger(GestionHechosService.class);
    private final WebClient webClient;
    private static WebApiCallerService webApiCallerService = null;
    private final String authServiceUrl;
    private static String hechosServiceUrl = "";



    @Autowired
    public GestionHechosService(WebApiCallerService webApiCallerService,
                                @Value("${auth.service.url}") String authServiceUrl,
                                @Value("${hechos.service.url}") String hechosServiceUrl){
        this.webClient = WebClient.builder().build();
        this.webApiCallerService = webApiCallerService;
        this.authServiceUrl = authServiceUrl;
        this.hechosServiceUrl = hechosServiceUrl;
    }

    public static HechoDTO obtenerHechoPorId(Long id) {
        HechoDTO response = webApiCallerService.get(hechosServiceUrl + "/hecho/" + id, HechoDTO.class);
        if (response == null) {
            throw new NotFoundException("Hecho", id);
        }
        return response;
    }

    public static boolean existeHecho(Long id) {
        try {
            obtenerHechoPorId(id);
            return true;
        } catch (NotFoundException e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar existencia del alumno: " + e.getMessage(), e);
        }
    }

    public HechoDTO crearHecho(HechoDTO hechoDTO) {
        HechoDTO response = webApiCallerService.post(hechosServiceUrl + "/hechos", hechoDTO, HechoDTO.class);
        if (response == null) {
            throw new RuntimeException("Error al crear hecho en el servicio externo");
        }
        return response;
    }

    public void eliminarHecho(Long id) {
        webApiCallerService.delete(hechosServiceUrl + "/hechos/" + id);
    }

    public boolean buscarCategoriaPorId(Long idCategoria) {
        try {
            obtenerCategoriaPorId(idCategoria);
            return true;
        } catch (NotFoundException e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar existencia de la categoria: " + e.getMessage(), e);
        }
    }

    //TODO POR AHI TIENE QUE HABER UN CONTROLLER DE CATEGORIAS PARA VER SI EXISTEN
    private void obtenerCategoriaPorId(Long idCategoria) {
        String response = webApiCallerService.get(hechosServiceUrl + "/categoria/" + idCategoria, String.class);
        if (response == null) {
            throw new NotFoundException("Categoria", idCategoria);
        }
    }
}
