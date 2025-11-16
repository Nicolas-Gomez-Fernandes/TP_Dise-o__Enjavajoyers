package ar.edu.frba.ddsi.servicio_agregador.schedulers;

import ar.edu.frba.ddsi.servicio_agregador.services.IColeccionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ColeccionScheduler {
  private final IColeccionService coleccionService;

  public ColeccionScheduler(IColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }

  @Scheduled(cron = "${cron.expression}")
  public void refrescarColecciones() {
    this.coleccionService.refrescarColecciones();
  }
  

}
