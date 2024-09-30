package com.ejercicio.recargas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejercicio.recargas.dto.CarrierDto;
import com.ejercicio.recargas.dto.PaquetesDto;
import com.ejercicio.recargas.entity.CarrierEntity;
import com.ejercicio.recargas.entity.PaquetesEntity;
import com.ejercicio.recargas.repository.CatalogosRepository;

@Service
public class CatalogosServiceImpl implements CatalogosService{

	@Autowired
	private CatalogosRepository catalogosRepository;

	@Override
	public List<CarrierDto> getCarrier() {
		return catalogosRepository.findAll().stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
	}

	@Override
	public List<PaquetesDto> getPaquetes() {
		return catalogosRepository.getPaquetes().stream()
				.map(this::convertDTO).collect(Collectors.toList());
	}

	/**
	 * Método para copiar datos de entidad a Dto
	 * @param carrier
	 * @return CarrierDto
	 */
    private CarrierDto convertirDTO(CarrierEntity carrier) {
        return new CarrierDto(carrier.getId(), carrier.getNombre());
    }
    
    /**
	 * Método para copiar datos de entidad a Dto
	 * @param paquetes
	 * @return PaquetesDto
	 */
    private PaquetesDto convertDTO(PaquetesEntity paquetes) {
        return new PaquetesDto(paquetes.getId(), paquetes.getNombre(), paquetes.getValor());
    }

}
