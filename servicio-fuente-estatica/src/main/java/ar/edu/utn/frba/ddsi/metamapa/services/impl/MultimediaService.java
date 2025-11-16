package ar.edu.utn.frba.ddsi.metamapa.services.impl;

import ar.edu.utn.frba.ddsi.metamapa.models.dtos.output.RtaCargaHechoOutputDTO;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.importador.ImportadorDeHechos;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Fuente;
import ar.edu.utn.frba.ddsi.metamapa.services.IHechosService;
import ar.edu.utn.frba.ddsi.metamapa.services.IMultimediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class MultimediaService implements IMultimediaService {
  @Autowired
  private FuentesService fuentesService;
  @Autowired
  private IHechosService hechosService;

  @Autowired
  ImportadorDeHechos importador;

  @Override
  public RtaCargaHechoOutputDTO cargarDataset(MultipartFile file) {
    if (file.isEmpty()) {
      CompletableFuture.completedFuture(RtaCargaHechoOutputDTO.builder()
          .mensaje("El archivo está vacío")
          .cantidadRegistros(0)
          .exito(false)
          .build());
    }

    log.info("Iniciando procesamiento del archivo: {}", file.getOriginalFilename());

    List<Hecho> hechos = importador.importarDataset(file).stream().map(this.hechosService::crearHecho).toList();
    hechos = this.hechosService.eliminarDuplicados(hechos);

    String origen = file.getOriginalFilename();
    Fuente fuente = this.fuentesService.createFuente(origen);

    hechos.forEach(h->h.setFuente(fuente));

    this.hechosService.guardarHechos(hechos);

    log.info("✅ Guardados {} hechos correctamente", hechos.size());

    return RtaCargaHechoOutputDTO.builder().mensaje("Se han cargado los hechos correctamente")
        .cantidadRegistros(hechos.size())
        .exito(true)
        .build();
  }

}
