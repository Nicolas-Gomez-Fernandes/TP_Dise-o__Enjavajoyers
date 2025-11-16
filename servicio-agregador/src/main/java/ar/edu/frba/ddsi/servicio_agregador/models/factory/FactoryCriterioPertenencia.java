package ar.edu.frba.ddsi.servicio_agregador.models.factory;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.input.*;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.CriterioCategoria;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.CriterioDescripcion;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.CriterioFecha;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.CriterioTitulo;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.CriterioUbicacion;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.ICriterioPertenencia;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios.TipoCriterio;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Categoria;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.hechos.Ubicacion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/*TODO
 * Fijarse si los criterios de pertenencia por ahi convenga que se creen sin parametro en el constructor o
 * pasar al metodo crearCriterioPertenencia(tipo, datos). El tema es que datos tendria que ser una interfaz que
 * sea polimorfico entre los datos de un criterio (que pueden variar)
 */

@Component
public class FactoryCriterioPertenencia {

  public static List<ICriterioPertenencia> crearCriterios(List<CriterioPertenenciaInputDTO> inputs) {
    if (inputs == null || inputs.isEmpty()) {
      throw new IllegalArgumentException("La lista de criterios no puede ser nula o estar vacía.");
    }
    return inputs.stream()
        .map(FactoryCriterioPertenencia::crearCriterioPertenencia)
        .collect(Collectors.toList());
  }


  public static ICriterioPertenencia crearCriterioPertenencia(CriterioPertenenciaInputDTO input) {
    // Validaciones iniciales
    if (input.getTipo() == null) {
      throw new IllegalArgumentException("El tipo no puede ser nulo.");
    }

    TipoCriterio tipo = input.getTipo();

    ICriterioPertenencia instancia = null;
    switch (tipo){
      case CATEGORIA:
        CriterioCategoriaInputDTO categoriaDTO = (CriterioCategoriaInputDTO)input;
        Categoria categoria = new Categoria(categoriaDTO.getCategoria());
        instancia = new CriterioCategoria(categoria); // TODO revisar si esta bien que categoria sea una clase
        break;
      case UBICACION:
          CriterioUbicacionInputDTO ubicacionDTO = (CriterioUbicacionInputDTO)input;
          instancia = new CriterioUbicacion(ubicacionDTO.getLatitud(), ubicacionDTO.getLongitud());
          break;
      case DESCRIPCION:
          CriterioDescripcionInputDTO descripcionDTO = (CriterioDescripcionInputDTO)input;
          instancia = new CriterioDescripcion(descripcionDTO.getDescripcion());
          break;
      case TITULO:
          CriterioTituloInputDTO tituloDTO = (CriterioTituloInputDTO)input;
          instancia = new CriterioTitulo(tituloDTO.getTitulo());
          break;
      case FECHA:
          CriterioFechaInputDTO fechaDTO = (CriterioFechaInputDTO)input;
          instancia = new CriterioFecha(fechaDTO.getDesde(), fechaDTO.getHasta());
          break;
      default:
          throw new IllegalArgumentException("Tipo de criterio no soportado: " + tipo);
    }

    return instancia;
  }
}
