package ar.edu.frba.ddsi.servicio_agregador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServicioAgregadorApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServicioAgregadorApplication.class, args);
  }

}
