package ar.edu.frba.ddsi.servicio_agregador.controllers;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.input.ColeccionInputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.ColeccionOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.HechoOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.paginacion.PageOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.TipoAlgoritmoDeConsenso;
import ar.edu.frba.ddsi.servicio_agregador.services.IColeccionService;
import ar.edu.frba.ddsi.servicio_agregador.services.impl.ColeccionService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agregador/colecciones")
public class ColeccionController {
  private IColeccionService coleccionService;

  public ColeccionController(ColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }

  @PostMapping
  public ResponseEntity<ColeccionOutputDTO> createColeccion(@RequestBody ColeccionInputDTO coleccion) {
    ColeccionOutputDTO nuevaColeccion = this.coleccionService.crearColeccion(coleccion);
    return new ResponseEntity<>(nuevaColeccion, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ColeccionOutputDTO> getColeccionById(@PathVariable Long id) {
    ColeccionOutputDTO coleccion = this.coleccionService.buscarPorId(id);
    return ResponseEntity.ok(coleccion);
  }

  @GetMapping("/{id}/hechos")
  public ResponseEntity<List<HechoOutputDTO>> getHechosByColeccionId(
      @PathVariable Long id,
      @RequestParam (defaultValue = "false") Boolean navegacionCurada,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta,
      @RequestParam(required = false) String provincia,
      @RequestParam(required = false) Long categoriaId,
      @RequestParam(required = false) Long fuenteId) {

    List<HechoOutputDTO> hechos = this.coleccionService.obtenerHechosFiltrados(id, navegacionCurada, fechaDesde, fechaHasta, provincia, categoriaId, fuenteId);
    return ResponseEntity.ok(hechos);
  }

  /**
   * Este método fue reemplazado por uno que soporta paginación.
   */
  /*@GetMapping
  public ResponseEntity<List<ColeccionOutputDTO>> getAllColeccion() {
    List<ColeccionOutputDTO> colecciones = this.coleccionService.buscarTodas();
    return ResponseEntity.ok(colecciones);
  }*/

  @GetMapping("/paged-personalizado")
  public ResponseEntity<PageOutputDTO<ColeccionOutputDTO>> getColeccionesPaginadasPersonalizado(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "12") Integer size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir) {

    Sort sort = sortDir.equalsIgnoreCase("desc")
        ? Sort.by(sortBy).descending()
        : Sort.by(sortBy).ascending();

    Pageable pageable = PageRequest.of(page, size, sort);
    PageOutputDTO<ColeccionOutputDTO> colecciones = this.coleccionService.buscarPaginadoPersonalizado(pageable);
    return ResponseEntity.ok(colecciones);
  }

  @GetMapping
  public ResponseEntity<Page<ColeccionOutputDTO>> getColeccionesPaginadas(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "12") Integer size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir) {

    Sort sort = sortDir.equalsIgnoreCase("desc")
        ? Sort.by(sortBy).descending()
        : Sort.by(sortBy).ascending();

    Pageable pageable = PageRequest.of(page, size, sort);
    Page<ColeccionOutputDTO> colecciones = this.coleccionService.buscarPaginado(pageable);
    return ResponseEntity.ok(colecciones);
  }


  @GetMapping("/destacadas")
  public ResponseEntity<List<ColeccionOutputDTO>> getColeccionesDestacadas() {
    List<ColeccionOutputDTO> colecciones = this.coleccionService.obtenerColeccionesDestacadas();
    return ResponseEntity.ok(colecciones);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ColeccionOutputDTO> updateColeccion(
      @PathVariable Long id,
      @RequestBody ColeccionInputDTO coleccion) {
    ColeccionOutputDTO coleccionActualizada = this.coleccionService.update(id, coleccion);
    return ResponseEntity.ok(coleccionActualizada);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteColeccion(@PathVariable Long id) {
    this.coleccionService.deleteColeccion(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/fuente/{idFuente}")
  public ResponseEntity<ColeccionOutputDTO> addFuente(
      @PathVariable Long id,
      @PathVariable Long idFuente) {
    ColeccionOutputDTO coleccionActualizada = this.coleccionService.agregarFuente(id, idFuente);
    return ResponseEntity.ok(coleccionActualizada);
  }

  @DeleteMapping("/{id}/fuente/{idFuente}")
  public ResponseEntity<Void> deleteFuente(
      @PathVariable Long id,
      @PathVariable Long idFuente) {
    this.coleccionService.desasociarFuente(id, idFuente);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("{id}/algoritmo")
  public ResponseEntity<Void> updateAlgoritmoDeConsenso(
      @PathVariable Long id,
      @RequestParam TipoAlgoritmoDeConsenso algoritmo) {
    this.coleccionService.updateAlgoritmoDeConsenso(id, algoritmo);
    return ResponseEntity.ok().build();
  }
}