package dev.diemigo.auditoria.controller;

// IMPORTACIONES DE SPRING Y HATEOAS
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// IMPORTACIONES DE SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

// IMPORTACIONES DE SPRING WEB Y VALIDACIÓN
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

// IMPORTACIONES DE TU PROYECTO
import dev.diemigo.dev.auditoria.dto.AuditoriaDTO;
import dev.diemigo.dev.auditoria.service.AuditoriaService;

// UTILIDADES DE JAVA
import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@Tag(name = "Gestión de Auditoría", description = "Endpoints para el registro masivo y la consulta de trazabilidad del sistema")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    // Inyección por constructor directa
    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @PostMapping
    @Operation(summary = "Registrar un log de auditoría", description = "Permite persistir un registro de trazabilidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Estructura de payload inválida")
    })
    public ResponseEntity<AuditoriaDTO> registrar(@Valid @RequestBody AuditoriaDTO dto) {
        AuditoriaDTO respuesta = auditoriaService.registrar(dto);

        // Enlaces HATEOAS
        respuesta.add(linkTo(methodOn(AuditoriaController.class).obtenerPorId(respuesta.getId())).withSelfRel());
        respuesta.add(linkTo(methodOn(AuditoriaController.class).obtenerTodos()).withRel("lista-completa"));

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping
    @Operation(summary = "Obtener todo el historial", description = "Recupera la lista histórica de logs")
    public ResponseEntity<List<AuditoriaDTO>> obtenerTodos() {
        List<AuditoriaDTO> lista = auditoriaService.obtenerTodos();

        // Enlaces HATEOAS
        lista.forEach(dto -> dto.add(linkTo(methodOn(AuditoriaController.class).obtenerPorId(dto.getId())).withSelfRel()));

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar auditoría por ID", description = "Busca en la base de datos un log específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro localizado"),
            @ApiResponse(responseCode = "404", description = "El identificador provisto no existe")
    })
    public ResponseEntity<AuditoriaDTO> obtenerPorId(@PathVariable Long id) {
        AuditoriaDTO dto = auditoriaService.obtenerPorId(id);

        // Enlaces HATEOAS
        dto.add(linkTo(methodOn(AuditoriaController.class).obtenerPorId(id)).withSelfRel());
        dto.add(linkTo(methodOn(AuditoriaController.class).obtenerTodos()).withRel("lista-completa"));

        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar registro de auditoría",
            description = "Elimina un registro de auditoría existente por su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        auditoriaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}