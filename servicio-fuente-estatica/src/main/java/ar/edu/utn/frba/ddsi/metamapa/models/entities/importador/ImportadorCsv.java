package ar.edu.utn.frba.ddsi.metamapa.models.entities.importador;

import ar.edu.utn.frba.ddsi.metamapa.models.dtos.input.HechoInputCsvDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.HechoCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class ImportadorCsv implements TipoImportador {
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Mono<List<Hecho>> importarHechosDesdePath(String rutaArchivo) {
        return Mono.fromCallable(() -> {
                try (FileReader fileReader = new FileReader(rutaArchivo)) {

                    // OpenCSV con CsvToBean - mapeo automático
                    CsvToBean<HechoCSV> csvToBean = new CsvToBeanBuilder<HechoCSV>(fileReader)
                        .withType(HechoCSV.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withIgnoreEmptyLine(true)
                        .withSkipLines(0) // Saltar encabezados
                        .build();

                    List<HechoCSV> hechosCSV = csvToBean.parse();

                    // Convertir DTO en Entidad usando mapper
                    return hechosCSV.stream()
                        .filter(dto -> dto != null && dto.getTitulo() != null) //que no le falte el titulo o sea nulo
                        .map(MapperHecho::mapear)
                        .toList();

                } catch (Exception e) {
                    throw new RuntimeException("Error al importar: " + rutaArchivo, e);
                }
            })
            .subscribeOn(Schedulers.boundedElastic()) // Ejecuta en hilo de I/O
            .doOnSubscribe(sub -> System.out.println("🔄 Importando: " + rutaArchivo))
            .doOnSuccess(hechos -> System.out.println("✅ " + hechos.size() + " hechos importados"))
            .doOnError(error -> System.err.println("❌ Error: " + error.getMessage()));
    }

  public List<HechoInputCsvDTO> importarDatasetMultiPartFile(MultipartFile file) {


    try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      // Configuración del mapeo de columnas
      HeaderColumnNameMappingStrategy<HechoCsvRepresentation> strategy = new HeaderColumnNameMappingStrategy<>();
      // Establece el tipo de clase que se va a mapear
      strategy.setType(HechoCsvRepresentation.class);
      // Configuración del CsvToBean
      CsvToBean<HechoCsvRepresentation> csvToBean = new CsvToBeanBuilder<HechoCsvRepresentation>(reader)
          .withMappingStrategy(strategy) // Establece la estrategia de mapeo
          .withIgnoreEmptyLine(true) // Ignora líneas vacías
          .withIgnoreLeadingWhiteSpace(true) // Ignora espacios en blanco al inicio de las líneas
          .build();

      return csvToBean.parse()
          .stream()
          .map(csvLine -> HechoInputCsvDTO.builder()
              .titulo(csvLine.getTitulo())
              .descripcion(csvLine.getDescripcion())
              .categoria(csvLine.getCategoria())
              .latitud(csvLine.getLatitud())
              .longitud(csvLine.getLongitud())
              .fecha(LocalDate.parse(csvLine.getFecha(), formatter).atStartOfDay())
              .build())
          .toList();
    } catch (IOException e) {
      throw new RuntimeException("Error al leer el archivo CSV",e);
    }
  }
}

