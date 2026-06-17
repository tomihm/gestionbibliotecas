package dev.diemigo.devoluciones.client;

import dev.diemigo.devoluciones.config.FeignConfig;
import dev.diemigo.devoluciones.dto.AuditoriaClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auditoria", path = "/api/auditoria", configuration = FeignConfig.class)
public interface AuditoriaClient {

    @PostMapping
    void registrarAccion(@RequestBody AuditoriaClientDTO dto);
}