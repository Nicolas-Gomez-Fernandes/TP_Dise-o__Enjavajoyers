package ar.edu.frba.ddsi.servicio_agregador.models.dtos.mapper;


import ar.edu.frba.ddsi.servicio_agregador.models.dtos.external.HechoResponseDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.HechoOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.UbicacionDto;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Ubicacion;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ManualHechoMapper {
  // TODO
  public static HechoOutputDTO mapHechoToHechoOutputDTO(Hecho hecho) {
    UbicacionDto ubi= new UbicacionDto(hecho.getUbicacion().getLatitud(), hecho.getUbicacion().getLongitud(), hecho.getProvincia());
    return HechoOutputDTO.builder().
        id(hecho.getId()).
        titulo(hecho.getTitulo()).
        descripcion(hecho.getDescripcion()).
        categoria(hecho.getCategoria().getNombre()).
        ubicacion(ubi).
        fecha(LocalDate.from(hecho.getFechaAcontecimiento())).
        fechaCreacion(hecho.getFechaCreacion()).
        fechaAcontecimiento(hecho.getFechaAcontecimiento()).
        build();
  }

  public static Hecho map(HechoResponseDTO hechosResponseDTO) {
    return Hecho.builder()
        .externalId(hechosResponseDTO.getId())
        .titulo(hechosResponseDTO.getTitulo())
        .descripcion(hechosResponseDTO.getDescripcion())
        .categoria(new Categoria(hechosResponseDTO.getCategoria()))
        .ubicacion(new Ubicacion(hechosResponseDTO.getLatitud(), hechosResponseDTO.getLongitud()))
        .fechaAcontecimiento(hechosResponseDTO.getFecha_hecho())
        .eliminado(hechosResponseDTO.getEliminado())
        .build();
  }
}
