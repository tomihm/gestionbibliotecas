package dev.diemigo.auditoria.assembler;

import dev.diemigo.dev.auditoria.controller.AuditoriaController;
import dev.diemigo.dev.auditoria.dto.AuditoriaDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AuditoriaModelAssembler implements RepresentationModelAssembler <AuditoriaDTO, AuditoriaDTO>{

    @Override
    public AuditoriaDTO toModel(AuditoriaDTO dto){
        dto.add(linkTo (methodOn( AuditoriaController.class).obtenerPorId(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(AuditoriaController.class).obtenerTodos()).withRel("Lista completa"));
        return dto;
    }
}
