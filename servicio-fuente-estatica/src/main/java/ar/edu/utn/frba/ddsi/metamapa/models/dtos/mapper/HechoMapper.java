package ar.edu.utn.frba.ddsi.metamapa.models.dtos.mapper;

import ar.edu.utn.frba.ddsi.metamapa.models.dtos.input.HechoInputCsvDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface HechoMapper {

  HechoMapper INSTANCE = Mappers.getMapper(HechoMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "titulo", target = "titulo")
  @Mapping(source = "descripcion", target = "descripcion")
  @Mapping(source = "categoria", target = "categoria")
  @Mapping(source = "ubicacion.latitud", target = "latitud")
  @Mapping(source = "ubicacion.longitud", target = "longitud")
  @Mapping(source = "fecha", target = "fechaDelHecho")
  @Mapping(source = "etiquetas", target = "", ignore = true)
  @Mapping(source = "eliminado", target = "", ignore = true)

  HechoOutputDTO hechoToHechoOutputDTO(Hecho hecho);
}
