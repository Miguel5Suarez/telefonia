package com.ejercicio.recargas.dto;

public class TelefoniaDto {
	private int id;
	private String numeroTelefonico;
	private int idTransaccion;
	private String fechaTransaccion;
	private int idPaquete;
	private int idCarrier;
	private int horaTransaccion;
	private int minutoTransaccion;

	public TelefoniaDto(int id, String numeroTelefonico, int idTransaccion, String fechaTransaccion, int idPaquete,
			int idCarrier, int horaTransaccion, int minutoTransaccion) {
		super();
		this.id = id;
		this.numeroTelefonico = numeroTelefonico;
		this.idTransaccion = idTransaccion;
		this.fechaTransaccion = fechaTransaccion;
		this.idPaquete = idPaquete;
		this.idCarrier = idCarrier;
		this.horaTransaccion = horaTransaccion;
		this.minutoTransaccion = minutoTransaccion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumeroTelefonico() {
		return numeroTelefonico;
	}

	public void setNumeroTelefonico(String numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}

	public int getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(int idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public String getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(String fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public int getIdPaquete() {
		return idPaquete;
	}

	public void setIdPaquete(int idPaquete) {
		this.idPaquete = idPaquete;
	}

	public int getIdCarrier() {
		return idCarrier;
	}

	public void setIdCarrier(int idCarrier) {
		this.idCarrier = idCarrier;
	}

	public int getHoraTransaccion() {
		return horaTransaccion;
	}

	public void setHoraTransaccion(int horaTransaccion) {
		this.horaTransaccion = horaTransaccion;
	}

	public int getMinutoTransaccion() {
		return minutoTransaccion;
	}

	public void setMinutoTransaccion(int minutoTransaccion) {
		this.minutoTransaccion = minutoTransaccion;
	}

}
