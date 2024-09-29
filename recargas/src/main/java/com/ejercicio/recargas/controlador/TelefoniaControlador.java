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
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("telefonia")
public class TelefoniaControlador {
	@Autowired
	private TelefoniaService telefoniaService;

	@PostMapping("/comprar")
	public ResponseEntity<String> comprar(@Valid @RequestBody CompraRequest compraReques) {
		return telefoniaService.comprar(compraReques);

	}

	@GetMapping("/compras/fecha")
	public List<TelefoniaEntity> obtenerVentasPorFechaActual() {
		return telefoniaService.totalVentas();
	}

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

	@GetMapping("/compras/carrier")
	public VentasDto obtenerVentasPorCarrier(@RequestParam(required = true) String carrier) {
		return telefoniaService.ventasPorCarrier(carrier);
	}

	@GetMapping("/compras/monto")
	public List<VentasDto> obtenerVentasPorMonto(
			@Min(value = 10, message = "El monto debe ser mayor o igual a 10.")
            @Max(value = 500, message = "El monto no puede ser mayor a 500.")
			@RequestParam(required = true) int monto) {
		return telefoniaService.ventasPorMonto(monto);
	}
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
