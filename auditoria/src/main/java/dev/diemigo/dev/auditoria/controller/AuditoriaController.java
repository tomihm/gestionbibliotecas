package dev.diemigo.dev.auditoria.controller;

import dev.diemigo.dev.auditoria.dto.AuditoriaDTO;
import dev.diemigo.dev.auditoria.service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
// @Tag agrupa y nombra el conjunto de endpoints en la interfaz gráfica
@Tag(name = "Gestión de Auditoría", description = "Endpoints para registrar y consultar bitácoras de acciones del sistema")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @PostMapping
    // @Operation define el resumen y la descripción detallada del endpoint
    @Operation(summary = "Registrar un evento", description = "Permite a otros microservicios reportar una acción para ser auditada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Auditoría registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Estructura de petición o payload inválido")
    })
    public ResponseEntity<AuditoriaDTO> registrar(@Valid @RequestBody AuditoriaDTO dto) {
        AuditoriaDTO respuesta = auditoriaService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping
    @Operation(summary = "Obtener lista completa", description = "Retorna el historial completo de logs de auditoría sin filtros")
    public ResponseEntity<List<AuditoriaDTO>> obtenerTodos() {
        return ResponseEntity.ok(auditoriaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Obtiene los detalles específicos de un registro de auditoría mediante su ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro localizado con éxito"),
            @ApiResponse(responseCode = "404", description = "El ID solicitado no existe en el sistema")
    })
    public ResponseEntity<AuditoriaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(auditoriaService.obtenerPorId(id));
    }
}