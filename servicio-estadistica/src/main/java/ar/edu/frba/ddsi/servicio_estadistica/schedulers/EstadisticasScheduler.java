package ar.edu.frba.ddsi.servicio_estadistica.schedulers;

import ar.edu.frba.ddsi.servicio_estadistica.services.IEstadisticasService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EstadisticasScheduler {
  private IEstadisticasService estadisticasService;

  public EstadisticasScheduler(IEstadisticasService estadisticasService) {
    this.estadisticasService = estadisticasService;
  }

  // todos los dias a las 12 del mediodia y a las 12 de la noche
  //@Scheduled(cron = "0 0 0,12 * * *")
  @Scheduled(fixedRate = 10000) // cada 10 segundos para pruebas
  public void actualizarEstadisticas() {
    // Lógica para actualizar estadísticas
    System.out.println("Actualizando estadísticas...");
    this.estadisticasService.actualizarEstadisticas();
    // Aquí se llamaría al servicio correspondiente para realizar la actualización
  }
}
