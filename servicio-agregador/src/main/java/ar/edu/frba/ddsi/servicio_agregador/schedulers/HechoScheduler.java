package ar.edu.frba.ddsi.servicio_agregador.schedulers;

import ar.edu.frba.ddsi.servicio_agregador.services.IHechosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HechoScheduler {
  private final IHechosService hechosService;
  private Boolean actualizacionHechosEnProceso = true;

  public HechoScheduler(IHechosService hechosService) {
    this.hechosService = hechosService;
  }

  /*@Scheduled(fixedRate = 60000) // cada 1 min
  public void obtenerHechosFuenteProxy() {
    this.hechosService.actualizarHechosProxyMetamapa();
  }*/
  @Scheduled(fixedRate = 60000 * 15) // cada 15 min
  public void actualizarHechos() {
    //this.hechosService.actualizarHechos();
    this.hechosService.actualizarYCargarNuevosHechos()
        .doOnSuccess(v -> log.info("Procesamiento de hechos completado"))
        .doOnError(e -> log.error("Error en el procesamiento de hechos: {}", e.getMessage()))
        .subscribe();
    actualizacionHechosEnProceso = false;
  }

  // todos los dias a las 3am @Scheduled(cron = "* * 3 * * * ")
  @Scheduled(fixedRate = 60000 * 1)
  public void aplicarAlgoritmoDeConsenso() {
    log.info("Aplicacion de algoritmos de hechos en proceso.");
     this.hechosService.aplicarAlgoritmoDeConsenso();
  }

  @Scheduled(fixedRate = 60000 * 10) // cada 10 min
  public void cargarProvincias(){
    if(actualizacionHechosEnProceso) {
      log.warn("Actualización de hechos en proceso. Se pospone la carga de provincias.");
      return;
    }
    this.hechosService.cargarProvinciasEnHechosSinUbicacion();
    actualizacionHechosEnProceso = true;
  }
}
