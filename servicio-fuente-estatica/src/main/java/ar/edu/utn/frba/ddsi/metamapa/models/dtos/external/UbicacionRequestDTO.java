package ar.edu.utn.frba.ddsi.metamapa.models.dtos.external;

import lombok.Data;

@Data
public class UbicacionRequestDTO {
  public UbicacionDTO ubicacion;
}
/*
    "parametros": {
    "aplanar": true,
    "campos": [
    "lat",
    "lon",
    "gobierno_local.nombre",
    "provincia.nombre",
    "departamento.nombre",
    "provincia.id",
    "gobierno_local.id",
    "departamento.id"
    ],
    "formato": "json",
    "lat": -32.8551545,
    "lon": -60.697636
    },
    "ubicacion": {
    "departamento_id": "82084",
    "departamento_nombre": "Rosario",
    "gobierno_local_id": "820196",
    "gobierno_local_nombre": "Granadero Baigorria",
    "lat": -32.8551545,
    "lon": -60.697636,
    "provincia_id": "82",
    "provincia_nombre": "Santa Fe"
    }
    }
*/