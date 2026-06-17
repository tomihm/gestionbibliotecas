package dev.diemigo.prestamos.controller;

import dev.diemigo.prestamos.dto.PrestamoDTO;
import dev.diemigo.prestamos.dto.PrestamoRespuestaDTO;
import dev.diemigo.prestamos.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService service;

    public PrestamoController(PrestamoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrestamoRespuestaDTO crear(@Valid @RequestBody PrestamoDTO dto) {
        return service.crear(dto);
    }

    @GetMapping
    public List<PrestamoRespuestaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PrestamoRespuestaDTO obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}