package ar.edu.frba.ddsi.servicio_agregador.models.entities.adapters;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.ubiApiGeoRef.UbicacionGeoRefDTO;

public interface IApiGeoRef{
    UbicacionGeoRefDTO getProvincia(Double latitud, Double longitud);
}
