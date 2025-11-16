package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequestDTO {
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fecha_nacimiento;
    private String contrasenia;
}
