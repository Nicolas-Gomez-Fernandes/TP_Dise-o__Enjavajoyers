package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos.solicitudEliminacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudEliminacionDTO {
    private Long id;
    private Long idHecho;
    private String tituloHecho;
    private String motivo;
    private LocalDateTime fechaSolicitud;
    private String estado; // PENDIENTE, ACEPTADA, RECHAZADA
    private Long idUsuario;
    private String nombreUsuario;
}
