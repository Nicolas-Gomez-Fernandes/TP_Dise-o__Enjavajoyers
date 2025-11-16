package ar.edu.frba.ddsi.servicio_agregador.models.dtos.mapper;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.ColeccionOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Coleccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = HechoMapper.class)  //usa HechoMapper
public interface ColeccionMapper {

  ColeccionMapper INSTANCE = Mappers.getMapper(ColeccionMapper.class);

  @Mapping(source = "titulo", target = "titulo")
  //@Mapping(source = "hechos", target = "hechos")  // MapStruct usará HechoMapper para convertir cada Hecho
  ColeccionOutputDTO coleccionToColeccionOutputDTO(Coleccion coleccion);
}