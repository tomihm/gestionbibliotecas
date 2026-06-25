package dev.diemigo.inventario.controller;

import dev.diemigo.inventario.assembler.InventarioModelAssembler;
import dev.diemigo.inventario.dto.InventarioDTO;
import dev.diemigo.inventario.exception.NotFoundException;
import dev.diemigo.inventario.exception.RequestException;
import dev.diemigo.inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioModelAssembler inventarioModelAssembler;

    @GetMapping
    @Operation(summary = "Obtener listado completo de inventario")
    @ApiResponse(responseCode = "200", description = "Mostrando listado")
    public ResponseEntity<List<InventarioDTO>> getInventarios() {

        List<InventarioDTO> inventario = inventarioService.listarInventarios()
                .stream()
                .map(inventarioModelAssembler::toModel)
                .collect(Collectors.toList());
        if(inventario.isEmpty()){
            throw new NotFoundException("No hay inventario registrado");
        }
        return ResponseEntity.ok(inventario);
    }



    @PostMapping
    @Operation(summary ="Crea un nuevo inventario" , description = "Permite registrar un inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registrado con éxito",
                    content = @Content(schema = @Schema(implementation = InventarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Estructura inválida", content = @Content)
    })
    public ResponseEntity<?> createInventario(@Valid @RequestBody InventarioDTO nuevoinventario) {

        if(nuevoinventario.getTitulo() ==  null || nuevoinventario.getTitulo().isEmpty()
                || nuevoinventario.getUbicacion() == null || nuevoinventario.getUbicacion().isEmpty()) {
            throw new RequestException("Falta un 1 o más campos por rellenar");
        }
        InventarioDTO respuesta = inventarioService.crearInventario(nuevoinventario);

        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioModelAssembler.toModel(respuesta));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta los datos de un inventario al buscar su ID única"
            ,description = "Busca coincidencias en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario encontrado"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado o no existe")
    })
    public ResponseEntity<InventarioDTO> getInventarioById(
            @Parameter(description = "ID del inventario", required = true, example = "1")
            @PathVariable Long id){

        InventarioDTO inventarioBuscado = inventarioService.buscarPorId(id);

        return ResponseEntity.ok(inventarioModelAssembler.toModel(inventarioBuscado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un inventario por su ID",
            description = "Permite modificar los datos de un inventario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    public ResponseEntity<InventarioDTO> updateInventario(
            @PathVariable Long id,
            @Valid @RequestBody InventarioDTO inventario) {

        InventarioDTO inventarioActualizado = inventarioService.actualizarInventario(id, inventario);

        return ResponseEntity.ok(inventarioModelAssembler.toModel(inventarioActualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un inventario por su ID", description = "Elimina un inventario de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Inventario eliminado"),
            @ApiResponse(responseCode = "404", description = "No se pudo eliminar: No existe inventario con esa ID")
    })
    public ResponseEntity<Void> deleteinventario(@PathVariable Long id) {
        var buscado = inventarioService.buscarPorId(id);
        if(buscado == null){
            throw new NotFoundException("Inventario no encontrado");
        }
        inventarioService.eliminarInventario(id);
        return ResponseEntity.noContent().build();
    }
}
