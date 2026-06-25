package dev.diemigo.inventario.assembler;


import dev.diemigo.inventario.controller.InventarioController;
import dev.diemigo.inventario.dto.InventarioDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class InventarioModelAssembler implements RepresentationModelAssembler <InventarioDTO, InventarioDTO>{

    @Override
    public InventarioDTO toModel(InventarioDTO dto){
        dto.add(linkTo(methodOn(InventarioController.class).getInventarios()).withRel("Lista completa"));
        return dto;
    }
}
