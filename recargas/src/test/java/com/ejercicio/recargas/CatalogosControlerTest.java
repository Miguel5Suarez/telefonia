package com.ejercicio.recargas;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ejercicio.recargas.controlador.CatalogosControlador;
import com.ejercicio.recargas.dto.CarrierDto;
import com.ejercicio.recargas.dto.PaquetesDto;
import com.ejercicio.recargas.service.CatalogosService;

@SpringBootTest
@AutoConfigureMockMvc
public class CatalogosControlerTest {
	@InjectMocks
	private CatalogosControlador catalogosControlador;
	@Mock
	private CatalogosService catalogosService;
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void consultaCarrierTest() throws Exception {
		CarrierDto carrier1 = new CarrierDto(1, "TELCEL");
		CarrierDto carrier2 = new CarrierDto(2, "MOVISTAR");
		List<CarrierDto> carriers = Arrays.asList(carrier1, carrier2);

		when(catalogosService.getCarrier()).thenReturn(carriers);
		mockMvc.perform(get("/catalogos/consulta/carrier").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void consultaPaquetesTest() throws Exception {
		PaquetesDto paquete1 = new PaquetesDto(1, "Paquete 1", 100);
		PaquetesDto paquete2 = new PaquetesDto(2, "Paquete 2", 200);
		List<PaquetesDto> paquetes = Arrays.asList(paquete1, paquete2);

		when(catalogosService.getPaquetes()).thenReturn(paquetes);
		mockMvc.perform(get("/catalogos/consulta/paquetes").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
