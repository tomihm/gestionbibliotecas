package dev.diemigo.auditoria.dto;

import com.fasterxml.jackson.annotation.JsonInclude;//consulta
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditoriaDTO extends RepresentationModel<AuditoriaDTO> { //evitar bucles con los enlances heredados

    private Long id;

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
