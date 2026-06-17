package dev.diemigo.prestamos.assembler;

import dev.diemigo.prestamos.controller.PrestamoController;
import dev.diemigo.prestamos.dto.PrestamoRespuestaDTO;
import dev.diemigo.prestamos.model.Prestamo;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PrestamoModelAssembler extends RepresentationModelAssemblerSupport<Prestamo, PrestamoRespuestaDTO> {

    public PrestamoModelAssembler() {
        super(PrestamoController.class, PrestamoRespuestaDTO.class);
    }

    @Override
    public PrestamoRespuestaDTO toModel(Prestamo entity) {
        // Conversión utilizando el Builder de Lombok
        PrestamoRespuestaDTO dto = PrestamoRespuestaDTO.builder()
                .id(entity.getId())
                .nombreCliente(entity.getNombreCliente())
                .libro(entity.getLibro())
                .fechaPrestamo(entity.getFechaPrestamo())
                .build();

        // Enlace HATEOAS autovinculado (self) al recurso individual
        dto.add(linkTo(methodOn(PrestamoController.class).obtener(entity.getId())).withSelfRel());

        // Enlace de conveniencia apuntando al listado completo
        dto.add(linkTo(methodOn(PrestamoController.class).listar()).withRel("lista-prestamos"));

        return dto;
    }
}