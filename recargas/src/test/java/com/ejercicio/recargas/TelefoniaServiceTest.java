package com.ejercicio.recargas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ejercicio.recargas.feing.AtytFeignClient;
import com.ejercicio.recargas.feing.MovistarFeignClient;
import com.ejercicio.recargas.feing.TelcelFeignClient;
import com.ejercicio.recargas.modelo.CompraRequest;
import com.ejercicio.recargas.repository.CatalogosRepository;
import com.ejercicio.recargas.repository.TelefoniaRepository;
import com.ejercicio.recargas.service.TelefoniaServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TelefoniaServiceTest {
	@InjectMocks
	private TelefoniaServiceImpl telefoniaService;

	@Mock
	private TelefoniaRepository telefoniaRepository;

	@Mock
	private CatalogosRepository catalogosRepository;

	@Mock
	private TelcelFeignClient telcelFeignClient;

	@Mock
	private MovistarFeignClient movistarFeignClient;

	@Mock
	private AtytFeignClient atytFeignClient;

	private CompraRequest compraRequest;

	@BeforeEach
	void setUp() {
		compraRequest = new CompraRequest();
		compraRequest.setNumeroTelefono("1234567890");
		compraRequest.setCarrier("TELCEL");
		compraRequest.setMonto(100);
	}

	@Test
	void comprarExitoTest() {

		when(catalogosRepository.getIdPaquete(100)).thenReturn(1);
		when(catalogosRepository.getIdCarrier("TELCEL")).thenReturn(1);
		when(telefoniaRepository.totalVentasFiltro("1234567890", 1)).thenReturn(null);

		ResponseEntity<String> response = telefoniaService.comprar(compraRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Proceso exitoso", response.getBody());
		verify(telcelFeignClient).comparPaquete(Mockito.any());
	}

	@Test
	void comprarExitoMovistarTest() {

		compraRequest.setCarrier("MOVISTAR");

		when(catalogosRepository.getIdPaquete(100)).thenReturn(1);
		when(catalogosRepository.getIdCarrier("MOVISTAR")).thenReturn(1);
		when(telefoniaRepository.totalVentasFiltro("1234567890", 1)).thenReturn("2024-09-30 17:49:06");

		ResponseEntity<String> response = telefoniaService.comprar(compraRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Proceso exitoso", response.getBody());
	}

	@Test
	void comprarExitoAtytTest() {

		compraRequest.setCarrier("AT&T");

		when(catalogosRepository.getIdPaquete(100)).thenReturn(1);
		when(catalogosRepository.getIdCarrier("AT&T")).thenReturn(1);
		when(telefoniaRepository.totalVentasFiltro("1234567890", 1)).thenReturn(null);

		ResponseEntity<String> response = telefoniaService.comprar(compraRequest);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Proceso exitoso", response.getBody());
	}
	
	@Test
    public void compraErrorTest() {
        String numeroTelefono = "1234567890";
        String carrier = "UNEFON";
        int monto = 10;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            telefoniaService.compraPaquete(numeroTelefono, carrier, monto);
        });

        assertEquals("Telefonia incorrecta UNEFON", exception.getMessage());
    }

	@Test
	void comprarMontoInvalidoTest() {
		compraRequest.setMonto(110);

		ResponseEntity<String> response = telefoniaService.comprar(compraRequest);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
		assertEquals("Los montos pueden ser 10, 30, 50, 100, 150, 200, 300, 500", response.getBody());
	}

	@Test
	void comprarCarrierInvalidoTest() {
		CompraRequest compraRequest = new CompraRequest();
		compraRequest.setNumeroTelefono("1234567890");
		compraRequest.setCarrier("INVALID_CARRIER");
		compraRequest.setMonto(100);

		when(catalogosRepository.getIdPaquete(100)).thenReturn(1);
		when(catalogosRepository.getIdCarrier("INVALID_CARRIER")).thenReturn(0);

		ResponseEntity<String> response = telefoniaService.comprar(compraRequest);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
		assertEquals("Los carrier pueden ser: TELCEL, MOVISTAR, AT&T", response.getBody());
	}

}
