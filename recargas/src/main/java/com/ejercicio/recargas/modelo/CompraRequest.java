package com.ejercicio.recargas.modelo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class CompraRequest {
	@NotEmpty(message = "El número de teléfono no puede estar vacío.")
	@Pattern(regexp = "^[0-9]{10}$", 
    message = "El número de teléfono debe contener exactamente 10 dígitos numéricos.")
	private String numeroTelefono;
	@NotEmpty(message = "El campo 'carrier' no puede estar vacío.")
	private String carrier;
	@NotNull(message = "El campo 'monto' no puede estar vacío.")
	@Positive(message = "El monto debe ser un número positivo.")
	@Min(value = 10, message = "El monto debe ser mayor o igual a 10.")
    @Max(value = 500, message = "El monto no puede ser mayor a 500.")
	private int monto;

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
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
