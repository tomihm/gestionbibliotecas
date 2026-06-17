package dev.diemigo.prestamos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrestamoRespuestaDTO extends RepresentationModel<PrestamoRespuestaDTO> {
    private Long id;
    private String nombreCliente;
    private String libro;
    private String fechaPrestamo;
}