package dev.diemigo.auditoria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import dev.diemigo.auditoria.dto.AuditoriaDTO;
import dev.diemigo.auditoria.service.AuditoriaService;
import dev.diemigo.auditoria.assembler.AuditoriaModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auditoria")
@Tag(name = "1. Gestión de Auditoría", description = "Endpoints para el registro, consulta y administración de la trazabilidad de microservicios")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;
    private final AuditoriaModelAssembler assembler;

    public AuditoriaController(AuditoriaService auditoriaService, AuditoriaModelAssembler assembler) {
        this.auditoriaService = auditoriaService;
        this.assembler = assembler;
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo log de auditoría", description = "Permite a otros microservicios (Libros, Usuarios, Préstamos) persistir un evento de auditoría.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro de auditoría creado exitosamente",
                    content = @Content(schema = @Schema(implementation = AuditoriaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Payload inválido o faltan campos obligatorios", content = @Content)
    })
    public ResponseEntity<AuditoriaDTO> registrar(@Valid @RequestBody AuditoriaDTO dto) {
        AuditoriaDTO respuesta = auditoriaService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(respuesta));
    }

    @GetMapping
    @Operation(summary = "Obtener el historial completo de auditorías", description = "Retorna un listado con todas las operaciones simuladas y reales registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    public ResponseEntity<List<AuditoriaDTO>> obtenerTodos() {
        List<AuditoriaDTO> lista = auditoriaService.obtenerTodos()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una auditoría por su ID único", description = "Busca en la base de datos relacional el registro correspondiente al ID enviado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El ID solicitado no existe en el sistema")
    })
    public ResponseEntity<AuditoriaDTO> obtenerPorId(
            @Parameter(description = "ID numérico del registro de auditoría", required = true, example = "1")
            @PathVariable Long id) {
        AuditoriaDTO dto = auditoriaService.obtenerPorId(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un registro de auditoría", description = "Elimina físicamente el registro de la base de datos (Operación restringida para usuarios ADMIN).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro eliminado correctamente (No Content)"),
            @ApiResponse(responseCode = "404", description = "No se pudo eliminar porque el ID no existe")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        auditoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}