package ar.edu.frba.ddsi.servicio_agregador.services.impl;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.input.SolicitudEliminacionInputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.SolicitudDeEliminacionOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Estado;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.SolicitudEliminacion;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.solicitudEliminacion.DetectorDeSpam;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.solicitudEliminacion.EstadoYHora;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.IHechosRepository;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.ISolicitudEliminacionRepository;
import ar.edu.frba.ddsi.servicio_agregador.services.ISolicitudDeEliminacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
public class SolicitudDeEliminacionService implements ISolicitudDeEliminacionService {
  private HechosService hechosService;
  private ISolicitudEliminacionRepository solicitudRepository;
  private DetectorDeSpam detectorDeSpam;

  public SolicitudDeEliminacionService(ISolicitudEliminacionRepository solicitudRepository, DetectorDeSpam detectorDeSpam, HechosService hechosService) {
    this.detectorDeSpam = detectorDeSpam;
    this.solicitudRepository = solicitudRepository;
    this.hechosService = hechosService;
  }

  @Override
  public SolicitudDeEliminacionOutputDTO crearSolicitud(SolicitudEliminacionInputDTO inputDTO) {
    log.info("Creando solicitud de eliminacion para el hecho con id: {} ", inputDTO.getId_hecho());
    if(inputDTO.getFundamento() == null ||inputDTO.getFundamento().length() < 500){
      throw new RuntimeException("El fundamento debe ser mayor que 500");
    }

    Hecho hecho = hechosService.getById(inputDTO.getId_hecho());

    SolicitudEliminacion solicitud = new SolicitudEliminacion(hecho, inputDTO.getFundamento());
    if (detectorDeSpam.esSpam(inputDTO.getFundamento())) {
      log.info("La solicitud de eliminacion fue marcada como spam y rechazada automaticamente.");
      solicitud.cambiarEstado(Estado.RECHAZADA_SPAM);
      solicitudRepository.save(solicitud);
      return this.solicitudToDTO(solicitud);
    }
    solicitud.cambiarEstado(Estado.PENDIENTE);
    solicitudRepository.save(solicitud);
    log.info("Solicitud de eliminacion {} creada ", solicitud.getId());
    return this.solicitudToDTO(solicitud);
  }

  @Override
  public List<SolicitudDeEliminacionOutputDTO> buscarTodos() {
    return solicitudRepository.findAll().stream().map(this::solicitudToDTO).toList();
  }



  public void aceptarSolicitudDeEliminacion(Long id) {
    var solicitudEliminacion = this.solicitudRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

    this.agregarHistorialEstadoPara(Estado.ACEPTADA, solicitudEliminacion);
    this.solicitudRepository.save(solicitudEliminacion);
  }

  private void agregarHistorialEstadoPara(Estado nuevoEstado, SolicitudEliminacion solicitudEliminacion) {
    solicitudEliminacion.setEstado(nuevoEstado);
    EstadoYHora estadoHoraActual = new EstadoYHora(nuevoEstado, LocalDateTime.now());
    solicitudEliminacion.getHistorialEstados().add(estadoHoraActual);
  }


  public void rechazarSolicitudDeEliminacion(Long id) {
    var solicitudEliminacion = this.solicitudRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

    this.agregarHistorialEstadoPara(Estado.RECHAZADA, solicitudEliminacion);
    this.solicitudRepository.save(solicitudEliminacion);
  }

  @Override
  public void actualizarEstadoDeSolicitud(Long id, boolean aceptado) {
    if(aceptado) {
      this.aceptarSolicitudDeEliminacion(id);
    } else {
      this.rechazarSolicitudDeEliminacion(id);
    }
  }


  private SolicitudDeEliminacionOutputDTO solicitudToDTO(SolicitudEliminacion solicitudEliminacion) {

    return new SolicitudDeEliminacionOutputDTO(
        solicitudEliminacion.getId(),
        solicitudEliminacion.getHechoAeliminar().getId(),
        solicitudEliminacion.getFundamento(),
        solicitudEliminacion.getEstado(),
        solicitudEliminacion.getFechaCreacion()
    );
  }
}