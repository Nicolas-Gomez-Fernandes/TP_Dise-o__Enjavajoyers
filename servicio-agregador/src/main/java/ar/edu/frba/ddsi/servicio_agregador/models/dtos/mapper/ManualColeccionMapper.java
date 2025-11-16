package ar.edu.frba.ddsi.servicio_agregador.models.dtos.mapper;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.ColeccionOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Coleccion;
import org.springframework.stereotype.Component;

public class ManualColeccionMapper {
  public ColeccionOutputDTO mapToDTO(Coleccion coleccion) {
    ColeccionOutputDTO dto = new ColeccionOutputDTO();
    dto.setId(coleccion.getId().toString());
    dto.setTitulo(coleccion.getTitulo());
    dto.setDescripcion(coleccion.getDescripcion());
    dto.setFuentes(coleccion.getFuentes());
    /*if (coleccion.getHechos() != null) {
      dto.setHechos(coleccion.getHechos().stream()
          .map(ManualHechoMapper::mapHechoToHechoOutputDTO)
          .toList());
    }*/
    return dto;
  }
}
