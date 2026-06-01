package dev.diemigo.dev.auditoria.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class AuditoriaDTO extends RepresentationModel<AuditoriaDTO> { //evitar bucles con los enlances heredados
    @NotBlank(message= "El servicio origen no puede estar vacio")
    private String servicioOrigen;

    @NotBlank(message = "La accion no puede estar vacio")
    private String accion;

    private String detalle;
    private String usuarioResponsable;

    @NotBlank (message = "El resultado es obligatorio")
    private String resultado;
    private LocalDateTime fechaHora;
}
