package ar.edu.frba.ddsi.servicio_estadistica.services.impl;

import ar.edu.frba.ddsi.servicio_estadistica.repositories.ICategoriaRepository;
import ar.edu.frba.ddsi.servicio_estadistica.repositories.IColeccionRepository;
import ar.edu.frba.ddsi.servicio_estadistica.services.IEstadisticasExportService;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class EstadisticasExportService implements IEstadisticasExportService {
    private IColeccionRepository coleccionRepository;
    private ICategoriaRepository categoriaRepository;

    public EstadisticasExportService(IColeccionRepository coleccionRepository,
                                       ICategoriaRepository categoriaRepository) {
        this.coleccionRepository = coleccionRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void exportarColecciones(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=estadisticas_colecciones.csv");

        // escribir BOM UTF-8 para que Excel lo detecte correctamente
        byte[] bom = new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF};
        response.getOutputStream().write(bom);

        Writer writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            String[] headers = {"idColeccion", "tituloColeccion", "cantidadHechos", "provincia"};
            csvWriter.writeNext(headers);

            List<Object[]> colecciones = coleccionRepository.findAllExport();

            for (Object[] data : colecciones) {
                String[] row = {
                    String.valueOf(data[0]),
                    String.valueOf(data[1]),
                    String.valueOf(data[2]),
                    String.valueOf(data[3])
                };
                csvWriter.writeNext(row);
            }
            csvWriter.flush();
        }
    }

    @Override
    public void exportarCategorias(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=estadisticas_categorias.csv");

        // escribir BOM UTF-8 para que Excel lo detecte correctamente
        byte[] bom = new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF};
        response.getOutputStream().write(bom);

        Writer writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            String[] headers = {"categoria", "provincia", "cantidad_hechos_provincia", "hora", "cantidad_hechos_hora"};
            csvWriter.writeNext(headers);

            List<Object[]> categorias = categoriaRepository.findAllExport();

            for (Object[] data : categorias) {
                String[] row = {
                    String.valueOf(data[0]),
                    String.valueOf(data[1]),
                    String.valueOf(data[2]),
                    String.valueOf(data[3]),
                    String.valueOf(data[4])
                };
                csvWriter.writeNext(row);
            }
            csvWriter.flush();
        }
    }
}
