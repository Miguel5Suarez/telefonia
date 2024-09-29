package com.ejercicio.recargas.service;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ejercicio.recargas.dto.VentasDto;
import com.ejercicio.recargas.entity.TelefoniaEntity;
import com.ejercicio.recargas.modelo.CompraRequest;
import com.ejercicio.recargas.repository.CatalogosRepository;
import com.ejercicio.recargas.repository.TelefoniaRepository;

@Service
public class TelefoniaServiceImpl implements TelefoniaService {
	private static final Logger logger = LogManager.getLogger(TelefoniaServiceImpl.class);
	@Autowired
	private TelefoniaRepository telefoniaRepository;
	@Autowired
	private CatalogosRepository catalogosRepository;

	@Override
	public ResponseEntity<String> comprar(CompraRequest compraRequest) {
		logger.info("Request: " + "numeroTelefono: " + compraRequest.getNumeroTelefono() + " " + "Carrier: "
				+ compraRequest.getCarrier() + " " + "Monto: " + compraRequest.getMonto());
		Integer idPaquete = obtenerIdPaquete(compraRequest.getMonto());
		if (idPaquete == 0) {
			return new ResponseEntity<String>("Los montos pueden ser 10, 30, 50, 100, 150, 200, 300, 500",
					HttpStatus.NOT_ACCEPTABLE);
		}
		int idCarrier = obtenerIdCarrier(compraRequest.getCarrier());
		if (idCarrier == 0) {
			return new ResponseEntity<String>("Los paquetes pueden ser: TELCEL, MOVISTAR, AT&T",
					HttpStatus.NOT_ACCEPTABLE);
		}

		if (limite(compraRequest.getNumeroTelefono(), idPaquete)) {
			TelefoniaEntity telefoniaEntity = new TelefoniaEntity();
			telefoniaEntity.setFechaTransaccion(formatoFecha());
			telefoniaEntity.setNumeroTelefonico(compraRequest.getNumeroTelefono());
			telefoniaEntity.setHoraTransaccion(getHora());
			telefoniaEntity.setMinutoTransaccion(getMinutos());
			telefoniaEntity.setIdCarrier(idCarrier);
			telefoniaEntity.setIdPaquete(idPaquete);
			telefoniaEntity.setIdTransaccion(generarIdTransaccion());
			telefoniaEntity.setFecha(new Date());
			telefoniaRepository.save(telefoniaEntity);

			logger.info("Fecha transacción: " + telefoniaEntity.getFechaTransaccion() + " " + "Número teléfono: "
					+ compraRequest.getNumeroTelefono() + " " + "IdTransaccion: " + telefoniaEntity.getIdTransaccion());
			return new ResponseEntity<String>("Proceso exitoso", HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("Favor de esperar 15 minutos", HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	public List<TelefoniaEntity> totalVentas() {
		return telefoniaRepository.totalVentas();
	}

	@Override
	public List<TelefoniaEntity> ventasPorRango(String fechaInicio, String fechaFin) {
		return telefoniaRepository.ventasPorRango(fechaInicio, fechaFin);
	}

	@Override
	public VentasDto ventasPorCarrier(String carrier) {
		String fecha = formatoFecha();
		return telefoniaRepository.ventasPorCarrier(carrier, fecha);
	}

	@Override
	public List<VentasDto> ventasPorMonto(int monto) {
		String fecha = formatoFecha();
		return telefoniaRepository.ventasPorMonto(monto, fecha);
	}

	@Override
	public List<VentasDto> ventasPorTelefono(int numeroTelefono, String fechaInicio, String fechaFin) {
		return telefoniaRepository.ventasPorTelefono(numeroTelefono, fechaInicio, fechaFin);
	}

	private String formatoFecha() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(new java.util.Date());
	}

	private int getHora() {
		return LocalTime.now().getHour();
	}

	private int getMinutos() {
		return LocalTime.now().getMinute();
	}

	private int generarIdTransaccion() {
		int min = 1;
		int max = 10000;
		return (int) (Math.random() * (max - min + 1) + min);
	}

	private int obtenerIdPaquete(int monto) {
		Integer idPaquete = catalogosRepository.getIdPaquete(monto);
		if (idPaquete == null) {
			return 0;
		}

		return idPaquete;
	}

	private int obtenerIdCarrier(String carrier) {
		Integer idPaquete = catalogosRepository.getIdCarrier(carrier);
		if (idPaquete == null) {
			return 0;
		}

		return idPaquete;
	}

	private boolean limite(String numeroTelefonico, int paquete) {
		Date fecha = telefoniaRepository.totalVentasFiltro(numeroTelefonico, paquete);
		if (fecha == null) {
			return true; // O manejarlo de otra manera según tu lógica
		}

		long milisegundos = fecha.getTime(); // El número en milisegundos
		Date fechas = new Date(milisegundos);
		System.out.println(fechas);

		return false;
	}

}
