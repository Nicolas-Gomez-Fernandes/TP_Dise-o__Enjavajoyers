package ar.edu.frba.ddsi.servicio_agregador.models.paginacion;

import org.springframework.data.domain.Page;

public class PageMapper {

  private final String basePath;

  public PageMapper(String basePath) {
    this.basePath = basePath;
  }

  public <T> PageOutputDTO<T> toPageOutputDTO(Page<T> page) {
    PageOutputDTO<T> dto = new PageOutputDTO<>();

    int currentPage = page.getNumber();
    int pageSize = page.getSize();
    int totalPages = page.getTotalPages();
    int numberOfElements = page.getNumberOfElements();
    long totalElements = page.getTotalElements();

    dto.setCurrentPage(currentPage);
    dto.setContent(page.getContent());

    // URLs de paginación
    dto.setFirstPageUrl(buildPageUrl(0, pageSize));
    dto.setLastPage(totalPages > 0 ? totalPages - 1 : 0);
    dto.setLastPageUrl(buildPageUrl(dto.getLastPage(), pageSize));
    dto.setNextPageUrl(page.hasNext() ? buildPageUrl(currentPage + 1, pageSize) : null);
    dto.setPrevPageUrl(page.hasPrevious() ? buildPageUrl(currentPage - 1, pageSize) : null);

    // Índices de elementos
    dto.setFrom(totalElements == 0 ? 0 : currentPage * pageSize + 1);
    dto.setTo(currentPage * pageSize + numberOfElements);
    dto.setNumberElements(numberOfElements);

    dto.setPerPage(pageSize);
    dto.setTotal((int) totalElements);
    dto.setPath(basePath);

    return dto;
  }

  private String buildPageUrl(int page, int size) {
    return String.format("%s?page=%d&size=%d", basePath, page, size);
  }
}

