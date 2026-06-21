package dev.proveedores.controller;
import dev.proveedores.model.DTO.proveedorDTO;
import dev.proveedores.model.proveedor;
import dev.proveedores.services.ProveedorService;
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
@RequestMapping("/api/proveedor")
@Tag(name = "Gestion de Proveedores", description = "Endpoints para administrar proveedores de libros y materiales de la biblioteca")
public class proveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    @Operation(summary = "Listar proveedores", description = "Obtiene todos los proveedores registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Listado de proveedores obtenido correctamente")
    public List<proveedor> findAll() {
        return proveedorService.findAll();
    }

    @GetMapping("Id")
    @Operation(summary = "Buscar proveedor por ID", description = "Obtiene la informacion resumida de un proveedor especifico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado",
                    content = @Content(schema = @Schema(implementation = proveedorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content)
    })
    public proveedorDTO findById(
            @Parameter(description = "ID numerico del proveedor", required = true, example = "1")
            @RequestParam Integer id) {
        return proveedorService.getproveedor(id);
    }

    @PutMapping("")
    @Operation(summary = "Actualizar proveedor", description = "Actualiza los datos de un proveedor existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = proveedor.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos", content = @Content)
    })
    public proveedor updateProveedor(@Valid @RequestBody proveedor proveedor) {
        return proveedorService.updateproveedor(proveedor);
    }

    @PostMapping("")
    @Operation(summary = "Crear proveedor", description = "Registra un nuevo proveedor para el sistema de biblioteca.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor creado correctamente",
                    content = @Content(schema = @Schema(implementation = proveedor.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos", content = @Content)
    })
    public proveedor addProveedor(@Valid @RequestBody proveedor proveedor) {
        return proveedorService.postproveedor(proveedor);
    }

    @DeleteMapping("")
    @Operation(summary = "Eliminar proveedor", description = "Elimina un proveedor usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content)
    })
    public void deleteProveedor(
            @Parameter(description = "ID numerico del proveedor", required = true, example = "1")
            @RequestParam Integer id) {
        proveedorService.deleteproveedor(id);
    }

}
