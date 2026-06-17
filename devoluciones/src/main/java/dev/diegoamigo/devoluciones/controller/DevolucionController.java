package dev.diemigo.devoluciones.controller;

import dev.diemigo.devoluciones.dto.DevolucionDTO;
import dev.diemigo.devoluciones.dto.DevolucionRespuestaDTO;
import dev.diemigo.devoluciones.service.DevolucionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/devoluciones")
public class DevolucionController {

    private final DevolucionService service;

    public DevolucionController(DevolucionService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DevolucionRespuestaDTO crear(@Valid @RequestBody DevolucionDTO dto) {
        return service.crear(dto);
    }

    @GetMapping
    public List<DevolucionRespuestaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public DevolucionRespuestaDTO obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}