package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.PageColeccionResponseDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.coleccion.ColeccionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.coleccion.PageColeccionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores.GestionColeccionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColeccionService {
  @Autowired
  private GestionColeccionesService gestionHechosService;

  public List<ColeccionDTO> obtenerTodasLasColecciones() {
    return this.gestionHechosService.obtenerTodasLasColecciones();
  }

  public List<ColeccionDTO> obtenerColeccionesDestacadas() {
    return this.gestionHechosService.obtenerColeccionesDestacadas();
  }

  public PageColeccionResponseDTO obtenerColeccionesPaginadoPersonalizado(Integer page, Integer size, String sortBy, String sortDir) {
    return this.gestionHechosService.obtenerColeccionesPaginadoPersonalizado(page, size, sortBy, sortDir);
  }

  public PageColeccionDTO obtenerColeccionesPaginado(Integer page, Integer size, String sortBy, String sortDir) {
    PageColeccionDTO coleccionesPage = this.gestionHechosService.obtenerColeccionesPaginado(page, size, sortBy, sortDir);
    return coleccionesPage;
  }
}
