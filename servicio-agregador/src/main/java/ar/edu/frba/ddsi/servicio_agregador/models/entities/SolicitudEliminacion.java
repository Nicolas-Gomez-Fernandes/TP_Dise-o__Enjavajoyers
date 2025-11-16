package ar.edu.frba.ddsi.servicio_agregador.models.entities;

import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.solicitudEliminacion.DetectorDeSpam;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.solicitudEliminacion.EstadoYHora;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "solicitudes_eliminacion")
public class SolicitudEliminacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hecho_id",nullable = false)
  private Hecho hechoAeliminar;
  @Column(name = "fundamento", nullable = false, length = 500)
  private String fundamento;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<EstadoYHora> historialEstados;
  @Enumerated(EnumType.STRING)
  private Estado estado;

  public SolicitudEliminacion(Hecho hecho, String fundamento) {
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
