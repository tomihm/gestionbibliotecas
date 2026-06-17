package dev.diegoamigo.multas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultaRespuestaDTO extends RepresentationModel<MultaRespuestaDTO> {
    private Long id;
    private Long prestamoId;
    private double monto;
    private String motivo;
    private boolean pagada;
}