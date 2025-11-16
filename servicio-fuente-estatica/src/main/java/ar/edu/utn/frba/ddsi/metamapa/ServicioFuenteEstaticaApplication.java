package ar.edu.utn.frba.ddsi.metamapa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ServicioFuenteEstaticaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioFuenteEstaticaApplication.class, args);
	}

}
