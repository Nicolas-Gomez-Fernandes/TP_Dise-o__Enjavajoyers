package ar.edu.utn.frba.ddsi.metamapa.services.impl;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Fuente;
import ar.edu.utn.frba.ddsi.metamapa.models.repositories.IFuentesRepository;
import ar.edu.utn.frba.ddsi.metamapa.services.IFuentesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FuentesService implements IFuentesService {
  private IFuentesRepository fuentesRepository;

  public FuentesService(IFuentesRepository fuentesRepository) {
    this.fuentesRepository = fuentesRepository;
  }

  @Override
  public Fuente createFuente(String path) {
    log.info("Creando fuente con path: {}", path);
    if(!this.fuentesRepository.existsByCsvPath(path)) {
      Fuente fuente = new Fuente();
      fuente.setCsvPath(path);
      return this.fuentesRepository.save(fuente);
    }
    else {
      return this.fuentesRepository.findByCsvPath(path);
    }
  }

  @Override
  public void deleteFuente(Long id) {
    this.fuentesRepository.deleteById(id);
  }


}
