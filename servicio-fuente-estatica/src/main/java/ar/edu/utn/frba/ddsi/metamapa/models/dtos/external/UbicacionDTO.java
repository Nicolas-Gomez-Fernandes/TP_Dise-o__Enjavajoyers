package ar.edu.utn.frba.ddsi.metamapa.models.dtos.external;


import lombok.Data;


@Data
public class UbicacionDTO {
  public String provincia_nombre;
  public String departamento_nombre;
  public String gobierno_local_nombre;
  public Double lat;
  public Double lon;
}
