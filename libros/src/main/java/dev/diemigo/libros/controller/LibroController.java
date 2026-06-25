package dev.diemigo.libros.controller;


import dev.diemigo.libros.assembler.LibrosModelAssembler;
import dev.diemigo.libros.dto.LibroDTO;
import dev.diemigo.libros.exception.NotFoundException;
import dev.diemigo.libros.exception.RequestException;
import dev.diemigo.libros.service.LibroService;
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
@RequestMapping("/api/v1/libros")
public class LibroController {
    @Autowired
    private LibroService libroService;

    @Autowired
    private LibrosModelAssembler libroModelAssembler;

    @GetMapping
    @Operation(summary = "Obtener listado completo de libros")
    @ApiResponse(responseCode = "200", description = "Mostrando listado")
    public ResponseEntity<List<LibroDTO>> getLibros() {

        List<LibroDTO> libros = libroService.listarLibros()
                .stream()
                .map(libroModelAssembler::toModel)
                .collect(Collectors.toList());
        if(libros.isEmpty()){
            throw new NotFoundException("No hay libros registrados");
        }
        return ResponseEntity.ok(libros);
    }



    @PostMapping
    @Operation(summary ="Crea un nuevo libro" , description = "Permite registrar un libro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registrado con éxito",
                    content = @Content(schema = @Schema(implementation = LibroDTO.class))),
            @ApiResponse(responseCode = "400", description = "Estructura inválida", content = @Content)
    })
    public ResponseEntity<?> createLibro(@Valid @RequestBody LibroDTO nuevoLibro) {

        if(nuevoLibro.getTitulo() ==  null || nuevoLibro.getTitulo().isEmpty()
        || nuevoLibro.getAutor() == null || nuevoLibro.getAutor().isEmpty()) {
            throw new RequestException("Al libro le falta Título o Autor");
        }
        LibroDTO respuesta = libroService.crearLibro(nuevoLibro);

        return ResponseEntity.status(HttpStatus.CREATED).body(libroModelAssembler.toModel(respuesta));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta los datos de un libro al buscar su ID única"
            ,description = "Busca coincidencias en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado o no existe")
    })
    public ResponseEntity<LibroDTO> getLibroById(
            @Parameter(description = "ID del Libro", required = true, example = "1")
            @PathVariable Long id){

        LibroDTO libroBuscado = libroService.buscarPorId(id);

        return ResponseEntity.ok(libroModelAssembler.toModel(libroBuscado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un libro por su ID",
            description = "Permite modificar los datos de un libro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    public ResponseEntity<LibroDTO> updateLibro(
            @PathVariable Long id,
            @Valid @RequestBody LibroDTO libro) {

        LibroDTO libroActualizado = libroService.actualizarLibro(id, libro);

        return ResponseEntity.ok(libroModelAssembler.toModel(libroActualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un Libro por su ID", description = "Elimina un Libro de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Libro eliminado"),
            @ApiResponse(responseCode = "404", description = "No se pudo eliminar: No existe libro con esa ID")
    })
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        var buscado = libroService.buscarPorId(id);
        if(buscado == null){
            throw new NotFoundException("Libro no encontrado");
        }
        libroService.eliminarLibro(id);
        return ResponseEntity.noContent().build();
    }
}
