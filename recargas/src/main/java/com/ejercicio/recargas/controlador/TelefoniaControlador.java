package com.ejercicio.recargas.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.recargas.dto.VentasDto;
import com.ejercicio.recargas.entity.TelefoniaEntity;
import com.ejercicio.recargas.modelo.CompraRequest;
import com.ejercicio.recargas.service.TelefoniaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("telefonia")
public class TelefoniaControlador {
	@Autowired
	private TelefoniaService telefoniaService;

	/**
	 * Método que realiza la compra de paquete telefónico
	 * @param compraRequest
	 * @return ResponseEntity<String>
	 */
	@PostMapping("/comprar")
	public ResponseEntity<String> comprar(@Valid @RequestBody CompraRequest compraRequest) {
		return telefoniaService.comprar(compraRequest);

	}

	/**
	 * Método para consultar el historico de ventas por dia
	 * @return List<TelefoniaEntity>
	 */
	@GetMapping("/compras/fecha")
	public List<TelefoniaEntity> obtenerVentasPorFechaActual() {
		return telefoniaService.totalVentas();
	}

	/**
	 * Método para conultar historico por rango de fechas
	 * @param fechaInicio
	 * @param fechaFin
	 * @return List<TelefoniaEntity>
	 */
	@GetMapping("/compras/fecha/rangos")
	public List<TelefoniaEntity> obtenerVentasPorRango(
			@Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", 
            message = "El formato de la fechaInicio debe ser YYYY-MM-DD.")
			@RequestParam(required = true) String fechaInicio,
			@Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", 
            message = "El formato de la fechaFin debe ser YYYY-MM-DD.")
			@RequestParam(required = true) String fechaFin) {
		return telefoniaService.ventasPorRango(fechaInicio, fechaFin);
	}

	/**
	 * Método para obtener el total de paquetes vendidos por carrier y monto del día actual
	 * @param carrier
	 * @return VentasDto
	 */
	@GetMapping("/compras/carrier")
	public List<VentasDto> obtenerVentasPorCarrier(
			@NotEmpty(message = "El campo 'carrier' no puede estar vacío.")
			@RequestParam(required = true) String carrier,
			@Min(value = 10, message = "El monto debe ser mayor o igual a 10.") 
			@Max(value = 500, message = "El monto no puede ser mayor a 500.") 
			@RequestParam(required = true) int monto) {
		return telefoniaService.ventasPorCarrier(carrier, monto);
	}

	/**
	 * Método para obtener el total de paquetes vendidos por monto
	 * @param monto
	 * @return List<VentasDto>
	 */
	@GetMapping("/compras/monto")
	public List<VentasDto> obtenerVentasPorMonto(
			@Min(value = 10, message = "El monto debe ser mayor o igual a 10.")
            @Max(value = 500, message = "El monto no puede ser mayor a 500.")
			@RequestParam(required = true) int monto) {
		return telefoniaService.ventasPorMonto(monto);
	}
	/**
	 * Método para obtener paquetes que ha comprado un número en particular de teléfono en un determinado periodo de tiempo
	 * @param numeroTelefono
	 * @param fechaInicio
	 * @param fechaFin
	 * @return List<VentasDto>
	 */
	@GetMapping("/compras/telefono")
	public List<VentasDto> obtenerVentasPorTelefono(
			@RequestParam(required = true) int numeroTelefono,
			@Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", 
            message = "El formato de la fechaInicio debe ser YYYY-MM-DD.")
			@RequestParam(required = true) String fechaInicio,
			@Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", 
            message = "El formato de la fechaFin debe ser YYYY-MM-DD.")
			@RequestParam(required = true) String fechaFin) {
		return telefoniaService.ventasPorTelefono(numeroTelefono, fechaInicio, fechaFin);
	}

}
