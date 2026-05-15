package dev.diegoamigo.devoluciones.controller;

import dev.diegoamigo.devoluciones.dto.DevolucionDTO;
import dev.diegoamigo.devoluciones.model.Devolucion;
import dev.diegoamigo.devoluciones.service.DevolucionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devoluciones")
public class DevolucionController {

    @Autowired
    private DevolucionService service;

    @PostMapping
    public Devolucion crear(@Valid @RequestBody DevolucionDTO dto) {

        return service.crear(dto);
    }

    @GetMapping
    public List<Devolucion> listar() {

        return service.listar();
    }

    @GetMapping("/{id}")
    public Devolucion obtener(@PathVariable Long id) {

        return service.obtener(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {

        service.eliminar(id);
    }
}