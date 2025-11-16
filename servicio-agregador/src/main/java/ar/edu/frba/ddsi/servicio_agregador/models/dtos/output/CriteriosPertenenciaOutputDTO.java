package ar.edu.frba.ddsi.servicio_agregador.models.dtos.output;
import lombok.Data;

@Data
public class CriteriosPertenenciaOutputDTO {
    private Long id;
    
    public CriteriosPertenenciaOutputDTO(Long id) {
        this.id = id;
    }
}
