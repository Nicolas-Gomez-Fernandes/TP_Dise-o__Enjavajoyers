package ar.edu.frba.ddsi.servicio_agregador.models.dtos.mapper;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.HechoOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Ubicacion;

@Mapper
public interface HechoMapper {

  //HechoMapper INSTANCE = Mappers.getMapper(HechoMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "titulo", target = "titulo")
  @Mapping(source = "descripcion", target = "descripcion")
  @Mapping(source = "categoria", target = "categoria")
  @Mapping(source = "ubicacion", target = "ubicacion")
  @Mapping(source = "fechaCreacion", target = "fechaCreacion")
  @Mapping(source = "fechaModificacion", target = "fechaModificacion")
  @Mapping(source = "rutaArchivoMultimedia", target = "rutaArchivoMultimedia")
  @Mapping(source = "tipoArchivoMultimedia", target = "tipoArchivoMultimedia")
  @Mapping(source = "etiquetas", target = "etiquetas")
  //HechoOutputDTO hechoToHechoOutputDTO(Hecho hecho);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "titulo", target = "titulo")
  @Mapping(source = "descripcion", target = "descripcion")
  @Mapping(source = "categoria", target = "categoria")
  @Mapping(source = "ubicacion", target = "ubicacion")
  @Mapping(source = "fechaModificacion", target = "fechaModificacion")
  @Mapping(source = "rutaArchivoMultimedia", target = "rutaArchivoMultimedia")
  @Mapping(source = "tipoArchivoMultimedia", target = "tipoArchivoMultimedia")
  @Mapping(source = "etiquetas", target = "etiquetas")
  //Hecho hechoOutputDTOToHecho(HechoOutputDTO h);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "titulo", target = "titulo")
  @Mapping(source = "descripcion", target = "descripcion")
  @Mapping(source = "categoria", target = "categoria")
  @Mapping(target = "ubicacion", expression = "java(mapUbicacion(dto.getLatitud(), dto.getLongitud()))")
  @Mapping(source = "fechaDelHecho", target = "fechaCreacion")
  @Mapping(target = "fechaUltimaEdicion", ignore = true)
  @Mapping(target = "imagen", ignore = true)
  @Mapping(target = "etiquetas", ignore = true)
  @Mapping(target = "origen", ignore = true)
  @Mapping(target = "eliminado", ignore = true)
  @Mapping(target = "rutaArchivoMultimedia", ignore = true)
  @Mapping(target = "tipoArchivoMultimedia", ignore = true)
  //Hecho hechoEstaticaToHecho(HechoOutputDTO dto);

  default Ubicacion mapUbicacion(Double latitud, Double longitud) {
    if (latitud == null && longitud == null) {
      return null;
    }
    Ubicacion ubicacion = new Ubicacion(latitud,latitud);
    return ubicacion;
  }

}
