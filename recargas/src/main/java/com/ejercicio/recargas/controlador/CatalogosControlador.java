package com.ejercicio.recargas.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.recargas.dto.CarrierDto;
import com.ejercicio.recargas.dto.PaquetesDto;
import com.ejercicio.recargas.service.CatalogosService;

@RestController
@RequestMapping("catalogos/consulta")
public class CatalogosControlador {
	@Autowired
	private CatalogosService catalogosService;

	@GetMapping("/carrier")
	public List<CarrierDto> consultaCarrier() {
		return catalogosService.getCarrier();
	}

	@GetMapping("/paquetes")
	public List<PaquetesDto> consultaPaquetes() {
		return catalogosService.getPaquetes();
	}

}
