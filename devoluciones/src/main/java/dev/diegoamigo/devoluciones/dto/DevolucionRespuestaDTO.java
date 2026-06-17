package dev.diemigo.devoluciones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DevolucionRespuestaDTO extends RepresentationModel<DevolucionRespuestaDTO> {
    private Long id;
    private Long prestamoId;
    private Long usuarioId;
    private LocalDate fechaDevolucionReal;
    private boolean atraso;
}