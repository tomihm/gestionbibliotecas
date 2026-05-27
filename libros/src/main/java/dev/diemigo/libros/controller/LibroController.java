package dev.diemigo.libros.controller;


import dev.diemigo.libros.dto.LibroDTO;
import dev.diemigo.libros.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/libros")
public class LibroController {
    @Autowired
    private LibroService libroService;

    @GetMapping
    public ResponseEntity<List<LibroDTO>> getLibros() {
        List<LibroDTO> usuarios = libroService.listarLibros();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<?> crearLibro(@Valid @RequestBody LibroDTO nuevoLibro) {
        try {
            LibroDTO libro = libroService.crearLibro(nuevoLibro);
            return ResponseEntity.status(HttpStatus.CREATED).body(libro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el libro: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLibroById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(libroService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Libro no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLibro(@PathVariable int id, @RequestBody LibroDTO libro) {
        try {
            return ResponseEntity.ok(libroService.actualizarLibro(id, libro));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo actualizar: Libro no encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLibro(@PathVariable int id) {
        try {
            libroService.eliminarLibro(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo eliminar: Libro no encontrado");
        }
    }
}
