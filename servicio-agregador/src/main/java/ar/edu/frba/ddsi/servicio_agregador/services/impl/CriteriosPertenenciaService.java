package ar.edu.frba.ddsi.servicio_agregador.services.impl;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.input.CriterioPertenenciaInputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.CriterioCategoria;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.CriterioDescripcion;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.CriterioFecha;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.CriterioTitulo;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.CriterioUbicacion;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.ICategoriasRepository;
import ar.edu.frba.ddsi.servicio_agregador.models.repositories.ICriteriosPertenenciaRepository;
import ar.edu.frba.ddsi.servicio_agregador.services.ICriteriosPertenenciaService;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.CriteriosPertenenciaOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.ICriterioPertenencia;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CriteriosPertenenciaService implements ICriteriosPertenenciaService {
  private ICriteriosPertenenciaRepository criteriosPertenenciaRepository;
  private ICategoriasRepository categoriaRepository;

  CriteriosPertenenciaService(ICriteriosPertenenciaRepository criteriosPertenenciaRepository, ICategoriasRepository categoriaRepository) {
    this.categoriaRepository = categoriaRepository;
    this.criteriosPertenenciaRepository = criteriosPertenenciaRepository;
  }

  @Override
  public ICriterioPertenencia crearCriterio(CriterioPertenenciaInputDTO criterioPertenenciaInputDTO) {

    // Validaciones iniciales
    if (criterioPertenenciaInputDTO.getTipo() == null) {
      //throw new IllegalArgumentException("El tipo no puede ser nulo.");
      return null;
    }

    switch (criterioPertenenciaInputDTO.getTipo()) {
      case CATEGORIA:
        Categoria categoria = this.categoriaRepository.findById(criterioPertenenciaInputDTO.getIdCategoria())
            .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con id: " + criterioPertenenciaInputDTO.getIdCategoria()));
        ICriterioPertenencia criterioCategoria = new CriterioCategoria(categoria);
        //return this.criteriosPertenenciaRepository.save(criterioCategoria);
        return criterioCategoria;
      case UBICACION:
        ICriterioPertenencia criterioUbicacion = new CriterioUbicacion(criterioPertenenciaInputDTO.getLatitud(), criterioPertenenciaInputDTO.getLongitud());
        //return this.criteriosPertenenciaRepository.save(criterioUbicacion);
        return criterioUbicacion;
      case DESCRIPCION:
        ICriterioPertenencia criterioDescripcion = new CriterioDescripcion(criterioPertenenciaInputDTO.getDescripcion());
        //return this.criteriosPertenenciaRepository.save(criterioDescripcion);
        return criterioDescripcion;
      case TITULO:
        ICriterioPertenencia criterioTitulo = new CriterioTitulo(criterioPertenenciaInputDTO.getTitulo());
        //return this.criteriosPertenenciaRepository.save(criterioTitulo);
        return criterioTitulo;
      case FECHA:
        ICriterioPertenencia criterioFecha = new CriterioFecha(criterioPertenenciaInputDTO.getDesde(), criterioPertenenciaInputDTO.getHasta());
        //return this.criteriosPertenenciaRepository.save(criterioFecha);
        return criterioFecha;
      default:
        throw new IllegalArgumentException("Tipo de criterio de pertenencia no reconocido: " + criterioPertenenciaInputDTO.getTipo());
    }
  }

  @Override
  public CriteriosPertenenciaOutputDTO buscarPorId(Long id) {
    ICriterioPertenencia criterioPertenencia = criteriosPertenenciaRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Criterio de pertenencia no encontrado con id: " + id));
    return criteriosPertenenciaOutputDTO(criterioPertenencia);
  }

  @Override
  public List<CriteriosPertenenciaOutputDTO> buscarTodos() {
    return this.criteriosPertenenciaRepository
        .findAll()
        .stream()
        .map(this::criteriosPertenenciaOutputDTO)
        .toList();
  }

  private CriteriosPertenenciaOutputDTO criteriosPertenenciaOutputDTO(ICriterioPertenencia criterioPertenencia) {
    return new CriteriosPertenenciaOutputDTO(
        criterioPertenencia.getId());
  }
}
