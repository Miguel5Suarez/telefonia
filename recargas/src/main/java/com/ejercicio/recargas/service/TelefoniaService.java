package com.ejercicio.recargas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ejercicio.recargas.dto.VentasDto;
import com.ejercicio.recargas.entity.TelefoniaEntity;
import com.ejercicio.recargas.modelo.CompraRequest;

public interface TelefoniaService {
	ResponseEntity<String> comprar(CompraRequest compraReques);

	List<TelefoniaEntity> totalVentas();
	
	List<TelefoniaEntity> ventasPorRango(String fechaInicio, String fechaFin);
	
	List<VentasDto> ventasPorCarrier(String carrier, int monto);
	
	List<VentasDto> ventasPorMonto(int monto);

	List<VentasDto> ventasPorTelefono(int numeroTelefono, String fechaInicio, String fechaFin);

}
