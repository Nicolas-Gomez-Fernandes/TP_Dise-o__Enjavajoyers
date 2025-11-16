package ar.edu.utn.frba.ddsi.metamapa.models.entities.importador;

import ar.edu.utn.frba.ddsi.metamapa.models.dtos.input.HechoInputCsvDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.dtos.mapper.HechoMapper;
import ar.edu.utn.frba.ddsi.metamapa.models.dtos.mapper.ManualHechoMapper;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component
public class ImportadorDeHechos {
  private static final String PDF = "pdf";
  private static final String CSV = "csv";
  private TipoImportador tipoImportador;

  public Mono<List<Hecho>> importar(String path) {
    String extension = obtenerExtension(path);


    // Importa segun la extension del archivo
    switch (extension.toLowerCase()) {
      case CSV:
        tipoImportador = new ImportadorCsv();
        return tipoImportador.importarHechosDesdePath(path);

      default:
        return Mono.error(new UnsupportedOperationException("Tipo de archivo no soportado: " + extension));
    }

  }

  // Importa el dataset desde un archivo MultipartFile
  public List<HechoInputCsvDTO> importarDataset(MultipartFile file) {
    String extension = obtenerExtensionPorContentType(file);

    // Importa segun la extension del archivo
    switch (extension.toLowerCase()) {
      case CSV:
        tipoImportador = new ImportadorCsv();
        return tipoImportador.importarDatasetMultiPartFile(file);
      default:
        throw new UnsupportedOperationException("Tipo de archivo no soportado: " + extension);
    }
  }

  // Retorna la extension del path
  private String obtenerExtension(String path) {
    int punto = path.lastIndexOf('.');
    return path.substring(punto + 1);
  }

  private String obtenerExtensionPorContentType(MultipartFile file) {
    String contentType = file.getContentType();
    if (contentType != null) {
      switch (contentType) {
        case "text/csv":
        case "application/vnd.ms-excel":
          return CSV;
        case "application/pdf":
          return PDF;
        default:
          throw new UnsupportedOperationException("Tipo de archivo no soportado: " + contentType);
      }
    } else {
      throw new UnsupportedOperationException("No se pudo determinar el tipo de archivo.");
    }
  }


}
