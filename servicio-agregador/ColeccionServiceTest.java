package ar.edu.frba.ddsi.servicio_agregador.services.impl;

import ar.edu.frba.ddsi.servicio_agregador.models.dtos.input.ColeccionInputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.dtos.output.ColeccionOutputDTO;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Coleccion;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Fuente;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Hecho;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.Usuario;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.algoritmos.TipoAlgoritmoDeConsenso;
import ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.ICriterioPertenencia;
import ar.edu.frba.ddsi.servicio_agregador.repositories.IColeccionesRepository;
import ar.edu.frba.ddsi.servicio_agregador.repositories.IFuentesRepository;
import ar.edu.frba.ddsi.servicio_agregador.repositories.IHechosRepository;
import ar.edu.frba.ddsi.servicio_agregador.services.ICriteriosPertenenciaService;
import ar.edu.frba.ddsi.servicio_agregador.services.IHechosService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ColeccionServiceTest {

}
