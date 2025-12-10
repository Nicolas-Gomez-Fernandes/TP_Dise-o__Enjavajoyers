package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.PageColeccionResponseDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.colecciones.ColeccionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.colecciones.PageColeccionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores.GestionColeccionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColeccionService {
  @Autowired
  private GestionColeccionesService gestionColeccionesService;

  public List<ColeccionDTO> obtenerTodasLasColecciones() {
    return this.gestionColeccionesService.obtenerTodasLasColecciones();
  }

  public List<ColeccionDTO> obtenerColeccionesDestacadas() {
    return this.gestionColeccionesService.obtenerColeccionesDestacadas();
  }

  public PageColeccionResponseDTO obtenerColeccionesPaginadoPersonalizado(Integer page, Integer size, String sortBy, String sortDir) {
    return this.gestionColeccionesService.obtenerColeccionesPaginadoPersonalizado(page, size, sortBy, sortDir);
  }

  public PageColeccionDTO obtenerColeccionesPaginado(Integer page, Integer size, String sortBy, String sortDir) {
    PageColeccionDTO coleccionesPage = this.gestionColeccionesService.obtenerColeccionesPaginado(page, size, sortBy, sortDir);
    return coleccionesPage;
  }

  public ColeccionDTO obtenerColeccionPorId(Long id) {
    return this.gestionColeccionesService.obtenerColeccionPorId(id);
  }

  public List<ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.hechos.HechoDTO> obtenerHechosDeColeccion(Long coleccionId) {
    return this.gestionColeccionesService.obtenerHechosDeColeccion(coleccionId);
  }
}
