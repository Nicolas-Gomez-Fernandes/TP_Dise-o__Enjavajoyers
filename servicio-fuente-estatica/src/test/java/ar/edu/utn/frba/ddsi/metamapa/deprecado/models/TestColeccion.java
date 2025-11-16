package ar.edu.utn.frba.ddsi.metamapa.deprecado.models;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import ar.edu.utn.frba.ddsi.metamapa.deprecado.models.criterios.CriterioDescripcion;
import ar.edu.utn.frba.ddsi.metamapa.deprecado.models.criterios.CriterioFecha;
import ar.edu.utn.frba.ddsi.metamapa.deprecado.models.criterios.CriterioPertenencia;
import ar.edu.utn.frba.ddsi.metamapa.deprecado.models.criterios.CriterioTitulo;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Categoria;
import ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos.Ubicacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestColeccion {

    //path mati: C:\Users\matua\Documents\Facu\3er Año\Diseño de Sistemas\Proyectos DDS\Prueba
    //path fran: "C:\\Users\\Usuario\\OneDrive - UTN.BA\\Materias 3er Nivel\\DDS-Diseño de Sistemas\\TPA-MetaMapa\\Fuentes de hechos\\casoDePruebaE1.csv"
    String pathPrueba;

    @BeforeEach
    public void init() {
        pathPrueba = "C:\\Users\\Usuario\\OneDrive - UTN.BA\\Materias 3er Nivel\\DDS-Diseño de Sistemas\\TPA-MetaMapa\\Fuentes de hechos\\casoDePruebaE1.csv";
    }
    // TESTS DEL ESCENARIO 1
    @Test
    public void asociarFuente() throws IOException {
        Coleccion coleccion = new Coleccion("Prueba", "Esto es una prueba");
        coleccion.asociarFuente(new EstaticaCSV(pathPrueba));

        assertFalse(coleccion.getHechos().isEmpty());
        Assertions.assertInstanceOf(Hecho.class, coleccion.getHechoIndex(1));
    }


    @Test
    public void aplicarVariosCriterios() throws IOException {
        Coleccion coleccion = new Coleccion("Prueba", "Esto es una prueba");

        CriterioPertenencia criterio1 = new CriterioFecha(LocalDate.of( 2008,1,01),LocalDate.of(2010,1,01));
        CriterioPertenencia criterio2 = new CriterioTitulo("Buenos Aires");
        CriterioDescripcion criterio3 = new CriterioDescripcion("Grave");

        coleccion.agregarCriterioDePertenencia(criterio1);
        coleccion.agregarCriterioDePertenencia(criterio2);
        coleccion.agregarCriterioDePertenencia(criterio3);

        coleccion.asociarFuente(new EstaticaCSV(pathPrueba));
        coleccion.aplicarCriteriosDePertenencia();

        // TODO Arreglar esta linea de abajo. Da true, siendo que la coleccion esta vacia
        // Assertions.assertFalse(coleccion.getHechos().stream().allMatch(h-> h.getTitulo().toLowerCase().contains("san juan")));
        Assertions.assertTrue(coleccion.getHechos().stream().allMatch(h-> h.getDescripcion().toLowerCase().contains("grave")));
        Assertions.assertTrue(coleccion.getHechos().stream().allMatch(h-> h.getFecha().isAfter(LocalDate.of(2008,1,01))));
    }
    @Test
    public void crearColeccionManual() throws IOException {
        Coleccion coleccion = new Coleccion("Prueba", "Esto es una prueba");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Hecho hecho1 = new Hecho(
                "Caída de aeronave impacta en Olavarría",
                "Grave caída de aeronave ocurrió en las inmediaciones de Olavarría, Buenos Aires. El incidente provocó pánico entre los residentes locales. Voluntarios de diversas organizaciones se han sumado a las tareas de auxilio.",
                new Categoria("Caída de aeronave"),
                new Ubicacion(-36.86683, -60.34327),
                LocalDate.parse("29/11/2001", formatter)
        );
        coleccion.agregarHecho(hecho1);


        Hecho hecho2 = new Hecho(
                "Serio incidente: Accidente con maquinaria industrial en Chos Malal, Neuquén",
                "Un grave accidente con maquinaria industrial se registró en Chos Malal, Neuquén. El incidente dejó a varios sectores sin comunicación. Voluntarios de diversas organizaciones se han sumado a las tareas de auxilio.",
                new Categoria("Accidente con maquinaria industrial"),
                new Ubicacion(-37.34557, -70.24185),
                LocalDate.parse("16/08/2001", formatter)
        );
        coleccion.agregarHecho(hecho2);


        Hecho hecho3 = new Hecho(
                "Caída de aeronave impacta en Venado Tuerto, Santa Fe",
                "Grave caída de aeronave ocurrió en las inmediaciones de Venado Tuerto, Santa Fe. El incidente destruyó viviendas y dejó a familias evacuadas. Autoridades nacionales se han puesto a disposición para brindar asistencia.",
                new Categoria("Caída de aeronave"),
                new Ubicacion(-33.76051, -61.92032),
                LocalDate.parse("08/08/2008", formatter)
        );
        coleccion.agregarHecho(hecho3);


        Hecho hecho4 = new Hecho(
                "Accidente en paso a nivel deja múltiples daños en Pehuajó, Buenos Aires",
                "Grave accidente en paso a nivel ocurrió en las inmediaciones de Pehuajó, Buenos Aires. El incidente generó preocupación entre las autoridades provinciales. El Ministerio de Desarrollo Social está brindando apoyo a los damnificados.",
                new Categoria("Accidente en paso a nivel"),
                new Ubicacion(-35.85511, -61.94059),
                LocalDate.parse("27/01/2020", formatter)
        );
        coleccion.agregarHecho(hecho4);


        Hecho hecho5 = new Hecho(
                "Devastador Derrumbe en obra en construcción afecta a Presidencia Roque Sáenz Peña",
                "Un grave derrumbe en obra en construcción se registró en Presidencia Roque Sáenz Peña, Chaco. El incidente generó preocupación entre las autoridades provinciales. El Intendente local se ha trasladado al lugar para supervisar las operaciones.",
                new Categoria("Derrumbe en obra en construcción"),
                new Ubicacion(-26.78008, -60.45782),
                LocalDate.parse("04/05/2016", formatter)
        );
        coleccion.agregarHecho(hecho5);
        //se observa que se agregan los hechos
        assertEquals(5, coleccion.getHechos().size());
        assertEquals(hecho1, coleccion.getHechoIndex(0));
        assertEquals(hecho5, coleccion.getHechoIndex(4));

        coleccion.eliminarHecho(hecho3);
        assertEquals(4, coleccion.getHechos().size());
        assertFalse(coleccion.getHechos().contains(hecho3));

        //se observa que queden con el indice correcto
        assertEquals(hecho4, coleccion.getHechoIndex(2));
        assertEquals(hecho5, coleccion.getHechoIndex(3));
    }
}