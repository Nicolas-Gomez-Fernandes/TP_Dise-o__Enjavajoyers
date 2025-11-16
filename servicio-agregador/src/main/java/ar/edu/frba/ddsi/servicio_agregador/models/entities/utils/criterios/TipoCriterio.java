package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.criterios;

public enum TipoCriterio {
  CATEGORIA,
  UBICACION,
  DESCRIPCION,
  TITULO,
  FECHA;

  public static TipoCriterio from(String value) {
    try {
      return TipoCriterio.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Tipo de criterio desconocido: " + value, e);
    }
  }

  }
