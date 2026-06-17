package dev.diemigo.devoluciones.assembler;

import dev.diemigo.devoluciones.controller.DevolucionController;
import dev.diemigo.devoluciones.dto.DevolucionRespuestaDTO;
import dev.diemigo.devoluciones.model.Devolucion;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DevolucionModelAssembler extends RepresentationModelAssemblerSupport<Devolucion, DevolucionRespuestaDTO> {

    public DevolucionModelAssembler() {
        super(DevolucionController.class, DevolucionRespuestaDTO.class);
    }

    @Override
    public DevolucionRespuestaDTO toModel(Devolucion entity) {
        // Creación del DTO con patrón Builder
        DevolucionRespuestaDTO dto = DevolucionRespuestaDTO.builder()
                .id(entity.getId())
                .prestamoId(entity.getPrestamoId())
                .usuarioId(entity.getUsuarioId())
                .fechaDevolucionReal(entity.getFechaDevolucionReal())
                .atraso(entity.isAtraso())
                .build();

        // Agregamos enlaces HATEOAS para navegación autodescriptiva
        dto.add(linkTo(methodOn(DevolucionController.class).obtener(entity.getId())).withSelfRel());
        dto.add(linkTo(methodOn(DevolucionController.class).listar()).withRel("lista-devoluciones"));

        return dto;
    }
}