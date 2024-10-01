package com.ejercicio.recargas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ejercicio.recargas.dto.VentasDto;
import com.ejercicio.recargas.entity.TelefoniaEntity;
import com.ejercicio.recargas.modelo.CompraRequest;

public interface TelefoniaService {
	/**
	 * Método que realiza compra de paquete telefonico
	 * @param compraReques
	 * @return ResponseEntity<String>
	 */
	ResponseEntity<String> comprar(CompraRequest compraReques);
	
	/**
	 * Método para consultar historico de ventas por día
	 * @return List<TelefoniaEntity>
	 */
	List<TelefoniaEntity> totalVentas();
	
	/**
	 * Método para consultar historico de ventas por rango de fechas
	 * @return List<TelefoniaEntity>
	 */
	List<TelefoniaEntity> ventasPorRango(String fechaInicio, String fechaFin);
	
	/**
	 * Método para consultar total de ventas del dia actual
	 * @return List<VentasDto>
	 */
	List<VentasDto> ventasTotal();
	
	/**
	 * Método para consultar total de ventas por carrier y monto del dia actual
	 * @return List<VentasDto>
	 */
	List<VentasDto> ventasPorCarrier(String carrier, int monto);
	
	/**
	 * Método para consultar total de ventas por  monto del dia actual
	 * @return List<VentasDto>
	 */
	List<VentasDto> ventasPorMonto(int monto);

	/**
	 * Método para consultar total de ventas por telefono en un determinado tiempo
	 * @return List<VentasDto>
	 */
	List<VentasDto> ventasPorTelefono(int numeroTelefono, String fechaInicio, String fechaFin);

}
