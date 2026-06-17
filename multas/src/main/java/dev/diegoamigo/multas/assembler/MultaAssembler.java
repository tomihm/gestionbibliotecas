package dev.diegoamigo.multas.assembler;

import dev.diegoamigo.multas.controller.MultaController;
import dev.diegoamigo.multas.dto.MultaRespuestaDTO;
import dev.diegoamigo.multas.model.Multa;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MultaAssembler extends RepresentationModelAssemblerSupport<Multa, MultaRespuestaDTO> {

    public MultaAssembler() {
        super(MultaController.class, MultaRespuestaDTO.class);
    }

    @Override
    public MultaRespuestaDTO toModel(Multa entity) {
        // Mapeamos los datos base desde la entidad hacia el DTO de respuesta
        MultaRespuestaDTO dto = MultaRespuestaDTO.builder()
                .id(entity.getId())
                .prestamoId(entity.getPrestamoId())
                .monto(entity.getMonto())
                .motivo(entity.getMotivo())
                .pagada(entity.isPagada())
                .build();

        // Agregamos enlace HATEOAS autovinculado (self) apuntando al detalle de la multa
        dto.add(linkTo(methodOn(MultaController.class).obtener(entity.getId())).withSelfRel());

        // Agregamos enlace de conveniencia para listar todas las multas
        dto.add(linkTo(methodOn(MultaController.class).listar()).withRel("lista-multas"));

        return dto;
    }
}