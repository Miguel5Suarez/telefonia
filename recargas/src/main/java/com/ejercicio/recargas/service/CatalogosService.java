package com.ejercicio.recargas.service;

import java.util.List;

import com.ejercicio.recargas.dto.CarrierDto;
import com.ejercicio.recargas.dto.PaquetesDto;

public interface CatalogosService {
	List<CarrierDto> getCarrier();

	List<PaquetesDto> getPaquetes();

}
