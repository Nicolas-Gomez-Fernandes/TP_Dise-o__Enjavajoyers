package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos;

import lombok.Data;

@Data
public class BasePageDTO {
  private Boolean last;
  private Integer totalElements;
  private Integer totalPages;
  private Boolean first;
  private Integer size;
  private Integer number;
  private Integer numberOfElements;
  private Boolean empty;
  private String path;
}
