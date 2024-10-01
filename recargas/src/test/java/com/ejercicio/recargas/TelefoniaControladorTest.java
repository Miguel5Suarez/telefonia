package com.ejercicio.recargas;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.ejercicio.recargas.controlador.TelefoniaControlador;
import com.ejercicio.recargas.entity.TelefoniaEntity;
import com.ejercicio.recargas.modelo.CompraRequest;
import com.ejercicio.recargas.service.TelefoniaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class TelefoniaControladorTest {

	@InjectMocks
	private TelefoniaControlador telefoniaControlador;
	@Mock
	private TelefoniaService telefoniaService;

	@Autowired
	private MockMvc mockMvc;

	private CompraRequest compraRequest;

	private ObjectMapper objectMapper;

	private TelefoniaEntity telefoniaEntity;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		compraRequest = new CompraRequest();
		compraRequest.setCarrier("carrier");
		compraRequest.setMonto(1);
		compraRequest.setNumeroTelefono("1234567890");

		objectMapper = new ObjectMapper();

		telefoniaEntity = new TelefoniaEntity();
		telefoniaEntity.setFechaTransaccion("2024-09-30 17:49:06");
		telefoniaEntity.setIdCarrier(1);
		telefoniaEntity.setIdPaquete(1);
		telefoniaEntity.setIdTransaccion(1);
		telefoniaEntity.setNumeroTelefonico("1234567890");
	}

	@Test
	public void guadarTest() throws JsonProcessingException, Exception {
		ResponseEntity<?> response = telefoniaControlador.comprar(compraRequest);
		assertNull(response);
	}

	@Test
	public void guadarErrorRequesTest() throws JsonProcessingException, Exception {
		compraRequest = new CompraRequest();

		mockMvc.perform(post("/telefonia/comprar").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(compraRequest))).andExpect(status().isBadRequest());
	}

	@Test
	public void comprasFechaTest() throws Exception {
		mockMvc.perform(get("/telefonia/compras/fecha").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void comprasPorRangoTest() throws Exception {

		mockMvc.perform(get("/telefonia/compras/fecha/rangos").param("fechaInicio", "2023-01-01")
				.param("fechaFin", "2023-01-31").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	public void totalVentasTest() throws Exception {
		mockMvc.perform(get("/telefonia/compras/total").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void comprasPorCarrierTest() throws Exception {

		mockMvc.perform(get("/telefonia/compras/carrier").param("carrier", "TELCEL").param("monto", "100")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	void comprasPorMontoTest() throws Exception {

		mockMvc.perform(get("/telefonia/compras/monto").param("monto", "100").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	void comprasPorTelefonoTest() throws Exception {

		mockMvc.perform(get("/telefonia/compras/telefono").param("numeroTelefono", "1234567890")
				.param("fechaInicio", "2023-01-01").param("fechaFin", "2023-01-31").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
}
