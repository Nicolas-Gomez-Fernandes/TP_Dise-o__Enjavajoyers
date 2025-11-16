package ar.edu.frba.ddsi.servicio_estadistica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.frba.ddsi.servicio_estadistica.repositories.ICategoriaRepository;
import ar.edu.frba.ddsi.servicio_estadistica.repositories.IColeccionRepository;
import ar.edu.frba.ddsi.servicio_estadistica.services.impl.EstadisticasExportService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class EstadisticaApplicationTests {

	@Test
	void exportarColecciones_generaCSVCorrecto() throws Exception {
		IColeccionRepository coleccionRepository = mock(IColeccionRepository.class);
		ICategoriaRepository categoriaRepository = mock(ICategoriaRepository.class);
		EstadisticasExportService service = new EstadisticasExportService(coleccionRepository, categoriaRepository);

		Object[] fila = {1L, "Colección A", 5, "Buenos Aires"};
		when(coleccionRepository.findAllExport()).thenReturn(Collections.singletonList(fila));
		// Alternativa: when(coleccionRepository.findAllExport()).thenReturn(List.<Object[]>of(fila));

		MockHttpServletResponse response = new MockHttpServletResponse();
		service.exportarColecciones(response);

		String csv = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);

		assertEquals("text/csv", response.getContentType());
		assertEquals("attachment; filename=estadisticas_colecciones.csv", response.getHeader("Content-Disposition"));
		assertTrue(csv.startsWith("idColeccion,tituloColeccion,cantidadHechos,provincia"));
		assertTrue(csv.contains("1,Colección A,5,Buenos Aires"));
		verify(coleccionRepository, times(1)).findAllExport();
	}

	@Test
	void exportarCategorias_generaCSVCorrecto() throws Exception {
		IColeccionRepository coleccionRepository = mock(IColeccionRepository.class);
		ICategoriaRepository categoriaRepository = mock(ICategoriaRepository.class);
		EstadisticasExportService service = new EstadisticasExportService(coleccionRepository, categoriaRepository);

		Object[] filaCat = {"Robo", "CABA", 10L, 14, 3L};
		when(categoriaRepository.findAllExport()).thenReturn(Collections.singletonList(filaCat));
		// Alternativa: when(categoriaRepository.findAllExport()).thenReturn(List.<Object[]>of(filaCat));

		MockHttpServletResponse response = new MockHttpServletResponse();
		service.exportarCategorias(response);

		String csv = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);

		assertEquals("text/csv", response.getContentType());
		assertEquals("attachment; filename=estadisticas_categorias.csv", response.getHeader("Content-Disposition"));
		assertTrue(csv.startsWith("categoria,provincia,cantidad_hechos_provincia,hora,cantidad_hechos_hora"));
		assertTrue(csv.contains("Robo,CABA,10,14,3"));
		verify(categoriaRepository, times(1)).findAllExport();
	}

}
