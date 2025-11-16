package ar.edu.utn.frba.ddsi.metamapa.deprecado.models;


import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;

import java.io.IOException;
import java.util.List;

public interface FuenteDatos {
     List<Hecho> cargarDatos() throws IOException;
}
