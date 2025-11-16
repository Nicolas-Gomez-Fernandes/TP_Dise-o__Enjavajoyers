package ar.edu.frba.ddsi.servicio_estadistica.models.adapter;

import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.ResponseCategoria;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.ResponseColeccion;
import ar.edu.frba.ddsi.servicio_estadistica.models.dtos.ResponseSpam;
import java.util.List;


public interface IAdapterAgregador {

  List<ResponseColeccion> topProvinciaConMasHechosDeColecciones();

  List<ResponseCategoria> estadisticaCategoria();

  //    List<ResponseCategoria> topCategoriaConMasHechos();
//    List<ResponseCategoria> topProvinciaConMasHechosDeCategorias();
//    List<ResponseCategoria> topHoraConMasHechos();
  ResponseSpam cantidadDeSolicitudesSpam();
}
