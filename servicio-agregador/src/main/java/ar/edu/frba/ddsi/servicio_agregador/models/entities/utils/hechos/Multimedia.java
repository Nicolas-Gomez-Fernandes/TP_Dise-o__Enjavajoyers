package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.external.dinamica.MultimediaResponseDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "multimedia")
public class Multimedia {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "url", nullable = false)
  private String rutaArchivo;
  @Column(name = "tipo_archivo", nullable = false)
  private String tipoArchivo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hecho_id")
  private Hecho hecho;

  public Multimedia(String rutaArchivo, String tipoArchivo) {
    this.rutaArchivo = rutaArchivo;
    this.tipoArchivo = tipoArchivo;
  }

  public static List<Multimedia> fromDTOList(List<MultimediaResponseDTO> multimedia) {
    return multimedia.stream()
        .map(m -> new Multimedia(m.getRutaArchivo(), m.getTipoArchivo()))
        .toList();
  }

  public static List<MultimediaResponseDTO> toDTOList(List<Multimedia> archivosMultimedia) {
    return archivosMultimedia.stream()
        .map(m -> new MultimediaResponseDTO(m.getRutaArchivo(), m.getTipoArchivo()))
        .toList();
  }
}