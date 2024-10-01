package com.ejercicio.recargas.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.ejercicio.recargas.modelo.TelefoniaRequest;

@FeignClient(name = "movistarClient", url = "http://localhost:8082/telefonia")
public interface MovistarFeignClient {
	@PostMapping("/movistar/recarga")
	void comparPaquete(TelefoniaRequest telefoniaRequest);
}
