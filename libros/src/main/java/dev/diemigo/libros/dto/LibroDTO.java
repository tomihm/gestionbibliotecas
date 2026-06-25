package dev.diemigo.libros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class LibroDTO extends RepresentationModel<LibroDTO> {


    @NotNull
    private String titulo;
    @NotBlank
    private String autor;
}
