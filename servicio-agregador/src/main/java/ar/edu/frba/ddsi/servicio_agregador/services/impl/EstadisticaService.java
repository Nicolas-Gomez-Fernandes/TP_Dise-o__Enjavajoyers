package ar.edu.frba.ddsi.servicio_agregador.services.impl;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.estadisticas.EstadisticaCategoriaOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.estadisticas.EstadisticaColeccionOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.estadisticas.EstadisticaSolicitudSpamOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Coleccion;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Estado;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.ICategoriasRepository;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.IColeccionesRepository;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.IHechosRepository;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.ISolicitudEliminacionRepository;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.querysDTO.IHoraCantidad;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.querysDTO.IProvinciaCantidad;
import ar.edu.frba.ddsi.servicio_agregador.services.IEstadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class EstadisticaService implements IEstadisticaService {
    @Autowired
    private ICategoriasRepository categoriasRepository;
    @Autowired
    private IHechosRepository hechosRepository;
    @Autowired
    private IColeccionesRepository coleccionRepository;
    @Autowired
    private ISolicitudEliminacionRepository solicitudEliminacionRepository;

    @Override
    public List<EstadisticaCategoriaOutputDTO> estadisticaCategorias() {
        return this.categoriasRepository.findAll().stream().map(this::armarEstadisticaParaCategoria).toList();
    }

    @Override
    public List<EstadisticaColeccionOutputDTO> estadisticaColeccion() {
        return this.coleccionRepository.findAll().stream().map(this::armarEstadisticaParaColeccion).toList();
        //TODO HACER EXECPCION PERSO;
    }

    @Override
    public EstadisticaSolicitudSpamOutputDTO estadisticaSpam() {
        EstadisticaSolicitudSpamOutputDTO estadisticaSolicitudSpamOutputDTO = new EstadisticaSolicitudSpamOutputDTO();
        Integer cantDeSolicitudes = this.solicitudEliminacionRepository.cantidadTotalDeSolicitudes();
        Integer cantDeSolicitudesSpam = this.solicitudEliminacionRepository.countByEstado(Estado.RECHAZADA_SPAM);

        estadisticaSolicitudSpamOutputDTO.setCantidadSolicitudesEliminacion(cantDeSolicitudes);
        estadisticaSolicitudSpamOutputDTO.setCantidadDeSpam(cantDeSolicitudesSpam);

        return estadisticaSolicitudSpamOutputDTO;
    }



    private EstadisticaColeccionOutputDTO armarEstadisticaParaColeccion(Coleccion coleccion) {
        EstadisticaColeccionOutputDTO estadisticaColeccionOutputDTO = new EstadisticaColeccionOutputDTO();

        //Buscar la provincia que presente mayor cantidad de hechos en esa coleccion
        List<IProvinciaCantidad> provinciasConHechos = coleccionRepository.provinciasConMasHechosEnColeccion(coleccion.getId());
        String provinciaMasHechos = provinciasConHechos.get(0).getProvincia();
        Integer cantHechosProvincia = provinciasConHechos.get(0).getCantidad();

        //Armar el Dto de salida
        estadisticaColeccionOutputDTO.setIdColeccion(coleccion.getId());
        estadisticaColeccionOutputDTO.setTituloColeccion(coleccion.getTitulo());
        estadisticaColeccionOutputDTO.setProvincia(provinciaMasHechos);
        estadisticaColeccionOutputDTO.setCantidadHechos(cantHechosProvincia);

        return estadisticaColeccionOutputDTO;
    }

    private EstadisticaCategoriaOutputDTO armarEstadisticaParaCategoria(Categoria c){
        EstadisticaCategoriaOutputDTO estadisticaCategoriaOutputDTO = new EstadisticaCategoriaOutputDTO();

        // Contar la cantidad de hechos de esa categoria
        Integer cantHechosCategoria = hechosRepository.cantHechosDeCategoria(c);

        //Buscar la provincia que presente mayor cantidad de hechos en esa categoria
        List<IProvinciaCantidad> provinciasConHechos = hechosRepository.provinciasConMasHechosEnCategoria(c);
        String provinciaMasHechos = provinciasConHechos.get(0).getProvincia();
        Integer cantHechosProvincia = provinciasConHechos.get(0).getCantidad();

        List<IHoraCantidad> cantidadDeHechosPorHora = hechosRepository.cantidadDeHechosPorHora(c);
        Integer horaMasHechos = cantidadDeHechosPorHora.get(0).getHora();
        Integer cantHechosHora = cantidadDeHechosPorHora.get(0).getCantidad();



        //Aramar el Dto de salida
        estadisticaCategoriaOutputDTO.setCategoria(c.getNombre());
        estadisticaCategoriaOutputDTO.setCantidad_hechos_categoria(cantHechosCategoria);
        estadisticaCategoriaOutputDTO.setProvincia(provinciaMasHechos);
        estadisticaCategoriaOutputDTO.setCantidad_hechos_provincia(cantHechosProvincia);
        estadisticaCategoriaOutputDTO.setHora(horaMasHechos);
        estadisticaCategoriaOutputDTO.setCantidad_hechos_hora(cantHechosHora);


        return estadisticaCategoriaOutputDTO;
    }
}
