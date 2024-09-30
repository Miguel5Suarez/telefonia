package com.ejercicio.recargas.service;

import java.util.List;

import com.ejercicio.recargas.dto.CarrierDto;
import com.ejercicio.recargas.dto.PaquetesDto;

public interface CatalogosService {
	/**
	 * Método para obtener listado de telefonias
	 * @return List<CarrierDto>
	 */
	List<CarrierDto> getCarrier();

	/**
	 * Método para obtener listado de paquetes telefónicos
	 * @return List<PaquetesDto>
	 */
	List<PaquetesDto> getPaquetes();

}
