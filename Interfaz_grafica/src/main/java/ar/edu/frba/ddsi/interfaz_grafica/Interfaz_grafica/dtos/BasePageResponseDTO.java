package ar.edu.frba.ddsi.interfaz_grafica.Interfaz_grafica.dtos;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class BasePageResponseDTO {
  private Integer currentPage; // paginas de 0 a n-1
  private String firstPageUrl;
  private String lastPageUrl;
  private String nextPageUrl;
  private String prevPageUrl;
  private Integer lastPage;
  private Integer perPage;
  private Integer from;
  private Integer to;
  private String path;
  private Integer total;

  // --- Métodos existentes ---
  public Integer getTotalPages() {
    return lastPage != null ? lastPage + 1 : 0; // convertir de índice a cantidad
  }

  public Integer paginaActual() {
    return currentPage + 1; // 1-based para mostrar en el front
  }

  // --- Métodos nuevos para Thymeleaf ---

  // Devuelve true si hay página anterior
  public Boolean hasPrevPage() {
    return currentPage != null && currentPage > 0;
  }

  // Devuelve true si hay página siguiente
  public Boolean hasNextPage() {
    return currentPage != null && currentPage < lastPage;
  }

  // Devuelve el número de la página anterior (0-based)
  public Integer prevPage() {
    return hasPrevPage() ? currentPage - 1 : 0;
  }

  // Devuelve el número de la página siguiente (0-based)
  public Integer nextPage() {
    return hasNextPage() ? currentPage + 1 : lastPage;
  }

  // Genera la lista de páginas visibles alrededor de la actual (0-based)
  public List<Integer> visiblePages() {
    List<Integer> pages = new ArrayList<>();
    if (currentPage == null || lastPage == null) return pages;

    int start = Math.max(0, currentPage - 2);
    int end = Math.min(lastPage, currentPage + 2);

    for (int i = start; i <= end; i++) {
      pages.add(i);
    }
    return pages;
  }
}
