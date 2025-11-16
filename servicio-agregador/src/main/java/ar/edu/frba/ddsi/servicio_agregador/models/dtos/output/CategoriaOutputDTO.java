package ar.edu.frba.ddsi.servicio_agregador.models.dtos.output;

import lombok.Data;

@Data
public class CategoriaOutputDTO {
    private Long id;
    private String nombre;

    public CategoriaOutputDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
