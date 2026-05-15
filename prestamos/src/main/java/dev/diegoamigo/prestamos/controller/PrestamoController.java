package dev.diegoamigo.prestamos.controller;

import dev.diegoamigo.prestamos.dto.PrestamoDTO;
import dev.diegoamigo.prestamos.model.Prestamo;
import dev.diegoamigo.prestamos.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService service;

    @PostMapping
    public Prestamo crear(@Valid @RequestBody PrestamoDTO dto) {

        return service.crear(dto);
    }

    @GetMapping
    public List<Prestamo> listar() {

        return service.listar();
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {

        service.eliminar(id);
    }
}