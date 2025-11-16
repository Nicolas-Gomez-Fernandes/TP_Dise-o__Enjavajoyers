package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services;

import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.HechoDTO;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.exceptions.DuplicateHechoException;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.exceptions.ValidationException;
import ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.services.gestores.GestionHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HechosService {
    @Autowired
    private GestionHechosService gestionHechosService;

    public HechoDTO crearHecho(HechoDTO hechoDTO) {
        // Validar datos del dto
        validarDuplicidadDeHecho(hechoDTO);
        validarDatosBasicos(hechoDTO);
        // crear
        return gestionHechosService.crearHecho(hechoDTO);
    }

    public HechoDTO obtenerHechoPorId(Long id) {
        return gestionHechosService.obtenerHechoPorId(id);
    }


    public void eliminarHecho(Long id) {
        gestionHechosService.obtenerHechoPorId(id); // Verificar que existe
        gestionHechosService.eliminarHecho(id);
    }

    private void validarDatosBasicos(HechoDTO hecho){
        ValidationException validationException = new ValidationException("Errores de validación");
        boolean tieneErrores = false;

        if (hecho.getTitulo() == null || hecho.getTitulo().trim().isEmpty()) {
            validationException.addFieldError("titulo", "El titulo es obligatorio");
            tieneErrores = true;
        }

        if (hecho.getDescripcion() == null || hecho.getDescripcion().trim().isEmpty()) {
            validationException.addFieldError("descripcion", "La descripcion es obligatoria");
            tieneErrores = true;
        }

        if (hecho.getFecha_acontecimiento() == null || hecho.getFecha_acontecimiento().toString().trim().isEmpty()) {
            validationException.addFieldError("fecha_acontecimiento", "La fecha del acontecimiento es obligatoria");
            tieneErrores = true;
        }

        if (hecho.getId_categoria() == null || gestionHechosService.buscarCategoriaPorId(hecho.getId_categoria())) {
            validationException.addFieldError("fecha_acontecimiento", "La fecha del acontecimiento es obligatoria");
            tieneErrores = true;
        }

        if (tieneErrores) {
            throw validationException;
        }
    }

    private void validarDuplicidadDeHecho(HechoDTO hechoDTO) {
        if (GestionHechosService.existeHecho(hechoDTO.getId())) {
            throw new DuplicateHechoException(hechoDTO.getTitulo());
        }
    }
}
