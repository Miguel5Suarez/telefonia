package com.ejercicio.recargas.dto;

public class VentasDto {
	private Long total;
	private Long cantidad;
	private String carrier;
	private int monto;

	public VentasDto(Long total, Long cantidad, String carrier, int monto) {
		super();
		this.total = total;
		this.cantidad = cantidad;
		this.carrier = carrier;
		this.monto = monto;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}


}
