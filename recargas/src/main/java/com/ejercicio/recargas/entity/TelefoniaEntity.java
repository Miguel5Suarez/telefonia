package com.ejercicio.recargas.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "HISTORICO_VENTAS")
public class TelefoniaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "numero_telefonico")
	private String numeroTelefonico;
	@Column(name = "id_transaccion")
	private int idTransaccion;
	@Column(name = "fecha_transaccion")
	private String fechaTransaccion;
	@Column(name = "id_paquete")
	private int idPaquete;
	@Column(name = "id_carrier")
	private int idCarrier;
	@Column(name = "hora_transaccion")
	private int horaTransaccion;
	@Column(name = "minuto_transaccion")
	private int minutoTransaccion;
	@Column(name = "fecha")
	private Date fecha;


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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
