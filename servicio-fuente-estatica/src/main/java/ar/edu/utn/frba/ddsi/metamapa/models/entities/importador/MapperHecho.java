package ar.edu.utn.frba.ddsi.metamapa.models.entities.importador;


import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.HechoCSV;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Ubicacion;


public class MapperHecho {
    public static Hecho mapear(HechoCSV dto) {
      Ubicacion ubicacion = new Ubicacion(dto.getLatitud(), dto.getLongitud());
      return new Hecho(
          dto.getTitulo(),
          dto.getDescripcion(),
          dto.getCategoria(),
          ubicacion,
          dto.getFecha()
      );
    }
  }
