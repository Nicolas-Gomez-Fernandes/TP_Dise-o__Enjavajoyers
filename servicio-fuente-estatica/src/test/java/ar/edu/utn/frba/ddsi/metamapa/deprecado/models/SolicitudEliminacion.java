package ar.edu.utn.frba.ddsi.metamapa.deprecado.models;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import ar.edu.utn.frba.ddsi.metamapa.deprecado.models.solicitudEliminacion.EstadoYHora;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SolicitudEliminacion {
  private Hecho hechoAeliminar;
  private String fundamento;
  @Getter
  private List<EstadoYHora> historialEstados;

  public SolicitudEliminacion(Hecho hecho, String fundamento) throws Exception {
    if(fundamento == null ||fundamento.length() < 500){
      throw new Exception("El fundamento debe ser mayor que 500");
    }
    this.hechoAeliminar = hecho;
    this.fundamento = fundamento;
    this.historialEstados = new ArrayList<>();
    historialEstados.add(new EstadoYHora(Estado.PENDIENTE, LocalDateTime.now()));
  }

  public Estado estadoActual() {
    return historialEstados.get(historialEstados.size() - 1).getEstado();
  }

  public void cambiarEstado(Estado nuevoEstado) {
    historialEstados.add(new EstadoYHora(nuevoEstado, LocalDateTime.now()));
    //esto deberia hacerlo el admin despues, entendemos que en otra capa
  }

  public LocalDateTime getFechaCreacion() {
    return this.historialEstados.get(0).getFecha();
  }
}
