package ar.edu.frba.ddsi.servicio_agregador.services;


import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.estadisticas.EstadisticaCategoriaOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.estadisticas.EstadisticaColeccionOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.estadisticas.EstadisticaSolicitudSpamOutputDTO;
import java.util.List;
import java.util.UUID;

public interface IEstadisticaService {
    List<EstadisticaCategoriaOutputDTO> estadisticaCategorias();

    List<EstadisticaColeccionOutputDTO> estadisticaColeccion();

    EstadisticaSolicitudSpamOutputDTO estadisticaSpam();
}
