package ar.edu.frba.ddsi.servicio_estadistica.services;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IEstadisticasExportService {
    void exportarColecciones(HttpServletResponse response) throws IOException;

    void exportarCategorias(HttpServletResponse response) throws IOException;
}
