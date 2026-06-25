package dev.diemigo.libros.assembler;


import dev.diemigo.libros.controller.LibroController;
import dev.diemigo.libros.dto.LibroDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class LibrosModelAssembler implements RepresentationModelAssembler <LibroDTO, LibroDTO>{

    @Override
    public LibroDTO toModel(LibroDTO dto){
        dto.add(linkTo(methodOn(LibroController.class).getLibros()).withRel("Lista completa"));
        return dto;
    }
}
