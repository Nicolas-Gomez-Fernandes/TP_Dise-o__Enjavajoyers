package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.solicitudEliminacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudEliminacionInputDTO {
    private Long id_hecho;
    private String fundamento;
}
