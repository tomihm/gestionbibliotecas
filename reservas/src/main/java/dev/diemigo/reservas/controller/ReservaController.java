package dev.diemigo.reservas.controller;

import dev.diemigo.reservas.dto.ReservaDTO;
import dev.diemigo.reservas.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Gestion de apartados de libros no disponibles actualmente")
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    @Operation(
            summary = "Registrar una reserva",
            description = "Permite apartar un libro que no se encuentra disponible actualmente"
    )
    @ApiResponse(responseCode = "201", description = "Reserva creada correctamente")
    public ResponseEntity<ReservaDTO> registrar(@Valid @RequestBody ReservaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.registrar(dto));
    }

    @GetMapping
    @Operation(summary = "Obtener todas las reservas")
    public ResponseEntity<List<ReservaDTO>> obtenerTodas() {
        return ResponseEntity.ok(reservaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una reserva por ID")
    public ResponseEntity<ReservaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.obtenerPorId(id));
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar una reserva activa")
    public ResponseEntity<ReservaDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelar(id));
    }
}
