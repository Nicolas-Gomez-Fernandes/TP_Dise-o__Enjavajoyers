package ar.edu.frba.ddsi.servicio_agregador.models.paginacion;

import lombok.Data;

import java.util.List;

// Sacado de desastres naturales
@Data
public class PageOutputDTO <T>{
  private Integer currentPage;
  private List<T> content;
  private String firstPageUrl;
  private String lastPageUrl;
  private String nextPageUrl;
  private String prevPageUrl;
  private Integer lastPage;
  private Integer perPage;
  private Integer from;
  private Integer to;
  private Integer numberElements;
  private String path;
  private Integer total;
}
