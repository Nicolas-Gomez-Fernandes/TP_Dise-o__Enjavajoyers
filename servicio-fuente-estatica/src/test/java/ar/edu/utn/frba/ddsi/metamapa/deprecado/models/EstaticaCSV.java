package ar.edu.utn.frba.ddsi.metamapa.deprecado.models;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.importador.ImportadorCsv;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Setter
public class EstaticaCSV implements FuenteDatos {

    private String path;
    private ImportadorCsv importadorCsv;

    public EstaticaCSV(String path) {
        this.importadorCsv = new ImportadorCsv();
        this.path = path;
    }

    public List<Hecho> cargarDatos() throws IOException {
        return List.of();
    }

}
