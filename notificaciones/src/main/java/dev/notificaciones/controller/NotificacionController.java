package dev.notificaciones.controller;

import dev.notificaciones.service.NotificacionService;
import dev.notificaciones.model.DTO.DTONotificaciones;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Gestion de Notificaciones", description = "Endpoints para gestionar notificaciones de entregas y avisos de biblioteca")
public class NotificacionController {
    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    @Operation(summary = "Listar notificaciones", description = "Obtiene todas las notificaciones registradas.")
    @ApiResponse(responseCode = "200", description = "Listado de notificaciones obtenido correctamente")
    public List<DTONotificaciones> findAll() {
        return notificacionService.obtenerNotificaciones();

    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar notificacion por ID", description = "Obtiene una notificacion especifica segun su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificacion encontrada",
                    content = @Content(schema = @Schema(implementation = DTONotificaciones.class))),
            @ApiResponse(responseCode = "404", description = "Notificacion no encontrada", content = @Content)
    })
    public DTONotificaciones findById(
            @Parameter(description = "ID numerico de la notificacion", required = true, example = "1")
            @PathVariable int id) {

        return notificacionService.getNotificacionesById(id);
    }

    @PostMapping
    @Operation(summary = "Crear notificacion", description = "Registra una nueva notificacion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificacion creada correctamente",
                    content = @Content(schema = @Schema(implementation = DTONotificaciones.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos", content = @Content)
    })
    public DTONotificaciones createNotificacion(@Valid @RequestBody DTONotificaciones notificacion) {
        return notificacionService.addNotificacion(notificacion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar notificacion", description = "Actualiza una notificacion existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificacion actualizada correctamente",
                    content = @Content(schema = @Schema(implementation = DTONotificaciones.class))),
            @ApiResponse(responseCode = "404", description = "Notificacion no encontrada", content = @Content)
    })
    public DTONotificaciones actualizarNotificacion(
            @Parameter(description = "ID numerico de la notificacion", required = true, example = "1")
            @PathVariable int id,
            @Valid @RequestBody DTONotificaciones notificacion) {
        return notificacionService.actualizarNotificacion(id, notificacion);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar notificacion", description = "Elimina una notificacion usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificacion eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Notificacion no encontrada", content = @Content)
    })
    public void deleteById(
            @Parameter(description = "ID numerico de la notificacion", required = true, example = "1")
            @PathVariable int id)
    {
        notificacionService.deleteNotificaciones(id);
    }

}
