package ar.edu.utn.frba.ddsi.metamapa.models.dtos.mapper;

import ar.edu.utn.frba.ddsi.metamapa.models.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.dtos.output.OrigenFuenteDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import org.springframework.stereotype.Component;

@Component
public class ManualHechoMapper {
  public static HechoOutputDTO map(Hecho hecho) {
    HechoOutputDTO dto = new HechoOutputDTO();
    dto.setId(hecho.getId());
    dto.setTitulo(hecho.getTitulo());
    dto.setDescripcion(hecho.getDescripcion());
    dto.setCategoria(hecho.getCategoria().getNombre());
    dto.setLatitud(hecho.getUbicacion().getLatitud());
    dto.setLongitud(hecho.getUbicacion().getLongitud());
    dto.setFecha_hecho(hecho.getFecha());
    dto.setEliminado(hecho.getEliminado());
    dto.setOrigen(new OrigenFuenteDTO(hecho.getFuente().getCsvPath(), "ESTATICA"));
    // Etiquetas y eliminado ignóralos
    return dto;
  }

 /* public static Hecho map(HechoInputCsvDTO dto) {
    Categoria categoria = new Categoria(dto.getCategoria());
    Ubicacion ubicacion = new Ubicacion(dto.getLatitud(), dto.getLongitud());

    return new Hecho(dto.getTitulo(), dto.getDescripcion(),categoria, ubicacion, dto.getFecha());
  }*/

}
