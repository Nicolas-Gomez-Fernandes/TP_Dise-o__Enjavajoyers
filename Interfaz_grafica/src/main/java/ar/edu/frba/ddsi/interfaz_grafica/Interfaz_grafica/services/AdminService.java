package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.colecciones.ColeccionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.solicitudEliminacion.SolicitudEliminacionDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores.GestionColeccionService;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores.GestionSolicitudEliminacionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final GestionColeccionService gestionColeccionService;
    private final GestionSolicitudEliminacionService gestionSolicitudEliminacionService;

    public AdminService(GestionColeccionService gestionColeccionService,
                       GestionSolicitudEliminacionService gestionSolicitudEliminacionService) {
        this.gestionColeccionService = gestionColeccionService;
        this.gestionSolicitudEliminacionService = gestionSolicitudEliminacionService;
    }

    // ========== GESTIÓN DE COLECCIONES ==========

    public ColeccionDTO crearColeccion(ColeccionDTO coleccion) {
        return gestionColeccionService.crearColeccion(coleccion);
    }

    public ColeccionDTO actualizarColeccion(ColeccionDTO coleccion) {
        return gestionColeccionService.actualizarColeccion(coleccion);
    }

    public void eliminarColeccion(Long id) {
        gestionColeccionService.eliminarColeccion(id);
    }

    public void asignarFuenteAColeccion(Long idColeccion, Long idFuente) {
        gestionColeccionService.asignarFuente(idColeccion, idFuente);
    }

    // ========== GESTIÓN DE SOLICITUDES ==========

    public List<SolicitudEliminacionDTO> obtenerSolicitudesPendientes() {
        return gestionSolicitudEliminacionService.obtenerSolicitudesPendientes();
    }

    public void procesarSolicitud(Long id, boolean aceptada) {
        gestionSolicitudEliminacionService.procesarSolicitud(id, aceptada);
    }
}
