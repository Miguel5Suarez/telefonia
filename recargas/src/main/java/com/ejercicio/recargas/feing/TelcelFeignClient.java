package com.ejercicio.recargas.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.ejercicio.recargas.modelo.TelefoniaRequest;

//@FeignClient(name = "telcelClient", url = "http://localhost:8082/telefonia")
public interface TelcelFeignClient {
	@PostMapping("/telcel/recarga")
	void comparPaquete(TelefoniaRequest telefoniaRequest);

}
