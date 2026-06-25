package dev.diemigo.inventario.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class InventarioDTO extends RepresentationModel<InventarioDTO> {

    @NotBlank(message = "Debe tener título")
    private String titulo;

    @NotBlank(message = "Debe indicar estado")
    private String estado;

    @NotBlank(message = "Debe indicar la ubicación")
    private String ubicacion;
}
