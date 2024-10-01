package com.ejercicio.recargas.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.ejercicio.recargas.modelo.TelefoniaRequest;

@FeignClient(name = "atytClient", url = "http://localhost:8082/telefonia")
public interface AtytFeignClient {
	@PostMapping("/atyt/recarga")
	void comparPaquete(TelefoniaRequest telefoniaRequest);

}
