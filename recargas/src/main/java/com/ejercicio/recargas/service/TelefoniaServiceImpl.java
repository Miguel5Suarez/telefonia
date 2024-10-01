package com.ejercicio.recargas.service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.ejercicio.recargas.feing.AtytFeignClient;
import com.ejercicio.recargas.feing.MovistarFeignClient;
import com.ejercicio.recargas.feing.TelcelFeignClient;
import com.ejercicio.recargas.modelo.CompraRequest;
import com.ejercicio.recargas.modelo.TelefoniaRequest;
import com.ejercicio.recargas.repository.CatalogosRepository;
import com.ejercicio.recargas.repository.TelefoniaRepository;

@Service
public class TelefoniaServiceImpl implements TelefoniaService {
	private static final Logger logger = LogManager.getLogger(TelefoniaServiceImpl.class);
	@Autowired
	private TelefoniaRepository telefoniaRepository;
	@Autowired
	private CatalogosRepository catalogosRepository;
	@Autowired
	private TelcelFeignClient telcelFeignClient;
	@Autowired
	private MovistarFeignClient movistarFeignClient;
	@Autowired
	private AtytFeignClient atytFeignClient;

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
			return new ResponseEntity<String>("Los carrier pueden ser: TELCEL, MOVISTAR, AT&T",
					HttpStatus.NOT_ACCEPTABLE);
		}

		if (limite(compraRequest.getNumeroTelefono(), idPaquete)) {
			compraPaquete(compraRequest.getNumeroTelefono(), compraRequest.getCarrier(), compraRequest.getMonto());
			guardarHistorico(compraRequest.getNumeroTelefono(), idCarrier, idPaquete);

			return new ResponseEntity<String>("Proceso exitoso", HttpStatus.OK);
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
	public List<VentasDto> ventasTotal() {
		return telefoniaRepository.ventasTotal();
	}

	@Override
	public List<VentasDto> ventasPorCarrier(String carrier, int monto) {
		return telefoniaRepository.ventasPorCarrier(carrier, /* fecha, */ monto);
	}

	@Override
	public List<VentasDto> ventasPorMonto(int monto) {
		return telefoniaRepository.ventasPorMonto(monto);
	}

	@Override
	public List<VentasDto> ventasPorTelefono(int numeroTelefono, String fechaInicio, String fechaFin) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Convertir las cadenas a LocalDate
		LocalDate fechaInicioAux = LocalDate.parse(fechaInicio, formatter);
		LocalDate fechaFinAux = LocalDate.parse(fechaFin, formatter);
		return telefoniaRepository.ventasPorTelefono(numeroTelefono, fechaInicioAux, fechaFinAux);
	}

	/**
	 * Método para formatear fecha
	 * @return String
	 */
	private String formatoFechaSegundos() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new java.util.Date());
	}

	/**
	 * Método para generar un id_transaccion de manera aleatoria
	 * @return int
	 */
	private int generarIdTransaccion() {
		int min = 1;
		int max = 10000;
		return (int) (Math.random() * (max - min + 1) + min);
	}

	/**
	 * Método para obtener el id_paquete del catálogo de paquetes telefónicos
	 * @param monto
	 * @return int
	 */
	private int obtenerIdPaquete(int monto) {
		Integer idPaquete = catalogosRepository.getIdPaquete(monto);
		if (idPaquete == null) {
			return 0;
		}

		return idPaquete;
	}

	/**
	 * Método para obtener el id_carrier del catálogo de carrier
	 * @param monto
	 * @return int
	 */
	private int obtenerIdCarrier(String carrier) {
		Integer idPaquete = catalogosRepository.getIdCarrier(carrier);
		if (idPaquete == null) {
			return 0;
		}

		return idPaquete;
	}

	/**
	 * Método para no permitir la compra del mismo paquete para el mismo número de teléfono durante 15 minutos
	 * @param numeroTelefonico
	 * @param paquete
	 * @return boolean
	 */
	private boolean limite(String numeroTelefonico, int paquete) {
		String fecha = telefoniaRepository.totalVentasFiltro(numeroTelefonico, paquete);
		if (fecha == null) {
			return true;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechaActual = sdf.format(new Date());

		// Convertir las cadenas a LocalDateTime
		LocalDateTime fechaAux = LocalDateTime.parse(fecha, formatter);
		LocalDateTime fechaActualAux = LocalDateTime.parse(fechaActual, formatter);

		// Calcular la duración entre las dos fechas
		Duration duration = Duration.between(fechaAux, fechaActualAux);
		long minutos = duration.toMinutes();

		if (minutos > 15) {
			return true;
		}

		return false;
	}

	/**
	 * Método para consumir el microservicio que realiza la compra del paquete de manera ficticia
	 * @param numeroTelefono
	 * @param carrier
	 * @param monto
	 */
	public void compraPaquete(String numeroTelefono, String carrier, int monto) {
		TelefoniaRequest telefoniaRequest = new TelefoniaRequest();
		telefoniaRequest.setNumeroTelefono(numeroTelefono);
		telefoniaRequest.setCarrier(carrier);
		telefoniaRequest.setMonto(monto);
		switch (carrier) {
		case "TELCEL":
			telcelFeignClient.comparPaquete(telefoniaRequest);
			break;
		case "MOVISTAR":
			movistarFeignClient.comparPaquete(telefoniaRequest);

			break;
		case "AT&T":
			atytFeignClient.comparPaquete(telefoniaRequest);

			break;
		default:
			throw new IllegalArgumentException("Telefonia incorrecta " + carrier);
		}

	}

	/**
	 * Método para guardar en tabla de historico
	 * @param numeroTelefono
	 * @param idCarrier
	 * @param idPaquete
	 */
	private void guardarHistorico(String numeroTelefono, int idCarrier, int idPaquete) {
		TelefoniaEntity telefoniaEntity = new TelefoniaEntity();
		telefoniaEntity.setFechaTransaccion(formatoFechaSegundos());
		telefoniaEntity.setNumeroTelefonico(numeroTelefono);
		telefoniaEntity.setIdCarrier(idCarrier);
		telefoniaEntity.setIdPaquete(idPaquete);
		telefoniaEntity.setIdTransaccion(generarIdTransaccion());
		telefoniaRepository.save(telefoniaEntity);
		logger.info("Response: " + " " + "Fecha transacción: " + telefoniaEntity.getFechaTransaccion() + " "
				+ "Número teléfono: " + numeroTelefono + " " + "IdTransaccion: " + telefoniaEntity.getIdTransaccion());
	}

}
