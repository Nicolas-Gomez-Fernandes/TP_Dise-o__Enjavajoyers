package ar.edu.utn.frba.ddsi.metamapa.config;

import ar.edu.utn.frba.ddsi.metamapa.models.dtos.input.HechoInputCsvDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.importador.ImportadorCsv;
import ar.edu.utn.frba.ddsi.metamapa.services.IHechosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * DataLoader: Carga automática de datos desde CSV al iniciar la aplicación.
 * Implementa ApplicationRunner para ejecutarse después de que el contexto de Spring esté listo.
 */
@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

    @Autowired
    private IHechosService hechosService;

    @Autowired
    private ImportadorCsv importadorCsv;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("🚀 Iniciando carga automática de datos desde CSV...");

        try {
            // Cargar archivo desde resources
            ClassPathResource resource = new ClassPathResource("hechos.csv");
            
            if (!resource.exists()) {
                log.warn("⚠️ El archivo hechos.csv no existe en resources. No se cargarán datos iniciales.");
                return;
            }

            // Crear un MultipartFile mock desde el archivo
            File csvFile = resource.getFile();
            MultipartFile multipartFile = new MockMultipartFile(csvFile);

            // Importar datos usando el ImportadorCsv
            log.info("📖 Leyendo archivo hechos.csv...");
            List<HechoInputCsvDTO> hechosDTO = importadorCsv.importarDatasetMultiPartFile(multipartFile);
            
            if (hechosDTO == null || hechosDTO.isEmpty()) {
                log.warn("⚠️ No se encontraron hechos en el archivo CSV");
                return;
            }

            log.info("📊 Se importaron {} hechos desde el CSV", hechosDTO.size());

            // Convertir DTOs a entidades y guardar
            List<Hecho> hechos = hechosDTO.stream()
                .map(dto -> hechosService.crearHecho(dto))
                .toList();

            hechosService.guardarHechos(hechos);
            
            log.info("✅ Carga automática completada exitosamente");

        } catch (Exception e) {
            log.error("❌ Error al cargar datos iniciales desde CSV: {}", e.getMessage(), e);
            // No lanzamos la excepción para no detener el arranque de la aplicación
        }
    }

    /**
     * Clase auxiliar para convertir un File en MultipartFile
     */
    private static class MockMultipartFile implements MultipartFile {
        private final File file;

        public MockMultipartFile(File file) {
            this.file = file;
        }

        @Override
        public String getName() {
            return file.getName();
        }

        @Override
        public String getOriginalFilename() {
            return file.getName();
        }

        @Override
        public String getContentType() {
            return "text/csv";
        }

        @Override
        public boolean isEmpty() {
            return file.length() == 0;
        }

        @Override
        public long getSize() {
            return file.length();
        }

        @Override
        public byte[] getBytes() throws java.io.IOException {
            try (FileInputStream fis = new FileInputStream(file)) {
                return fis.readAllBytes();
            }
        }

        @Override
        public InputStream getInputStream() throws java.io.IOException {
            return new FileInputStream(file);
        }

        @Override
        public void transferTo(File dest) throws java.io.IOException, IllegalStateException {
            throw new UnsupportedOperationException("No soportado en MockMultipartFile");
        }
    }
}
