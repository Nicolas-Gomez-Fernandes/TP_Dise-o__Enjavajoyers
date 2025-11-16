package ar.edu.utn.frba.ddsi.metamapa.deprecado.models;


import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Categoria;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Ubicacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestSolicitudesDeEliminacion {

  Hecho hechoAEliminar;

  @BeforeEach
  public void init() {
    hechoAEliminar = new Hecho(
        "Brote de enfermedad contagiosa causa estragos en San Lorenzo, Santa Fe",
        "Grave brote de enfermedad contagiosa ocurrió en las inmediaciones de San Lorenzo, Santa Fe. El incidente dejó varios heridos y daños materiales. Se ha declarado estado de emergencia en la región para facilitar la asistencia.",
        new Categoria("Evento sanitario"),
        new Ubicacion(-32.786098, -60.741543),
        LocalDate.of(2005, 7, 5)
    );
  }

  @Test
  public void testSolicitudDeEliminacionInicial() throws Exception {

    SolicitudEliminacion solicitud = new SolicitudEliminacion(hechoAEliminar, "a".repeat(501));

    // Verificamos que la solicitud esta pendiente
    Assertions.assertEquals(Estado.PENDIENTE, solicitud.estadoActual());
  }

  @Test
  public void testSolicitudDeEliminacionRechazado() throws Exception {

    SolicitudEliminacion solicitud = new SolicitudEliminacion(hechoAEliminar, "a".repeat(500));

    LocalDateTime fechaDiaDespues = LocalDateTime.now().plusDays(1);

    //rechazar solicitud si paso un dia de su creacion
    if (fechaDiaDespues.getDayOfYear() - solicitud.getFechaCreacion().getDayOfYear() >= 1){ // compara que la cantidad de dias de diferencia es igual a 2
      solicitud.cambiarEstado(Estado.RECHAZADA);
    } else {
      solicitud.cambiarEstado(Estado.PENDIENTE);
    }

    // Verificamos que la solicitud esta rechazada
    Assertions.assertEquals(Estado.RECHAZADA, solicitud.estadoActual());
  }
  @Test
  public void testSolicitudDeEliminacionAceptada() throws Exception {
    Coleccion coleccion = new Coleccion("descripcion", "nombre");
    SolicitudEliminacion solicitud = new SolicitudEliminacion(hechoAEliminar, "a".repeat(501));

    //agregar el hecho a la coleccion
    coleccion.agregarHecho(hechoAEliminar);
    LocalDateTime fechaDiaDespues = LocalDateTime.now().plusHours(3);

    //aceptar solicitud si pasaron dos horas de su creacion
    if (fechaDiaDespues.getHour() - solicitud.getFechaCreacion().getHour() >= 2){
      solicitud.cambiarEstado(Estado.ACEPTADA);
      //elimino el hecho de la coleccion
      coleccion.eliminarHecho(hechoAEliminar);
    } else {
      solicitud.cambiarEstado(Estado.PENDIENTE);
    }

    // Verificamos que la solicitud esta Aceptada
    Assertions.assertEquals(Estado.ACEPTADA, solicitud.estadoActual());

    // Verificamos que el hecho fue eliminado de la coleccion
    Assertions.assertFalse(coleccion.getHechos().contains(hechoAEliminar));
  }
}
