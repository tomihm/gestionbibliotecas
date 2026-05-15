package dev.diegoamigo.prestamos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    @NotBlank(message = "El libro es obligatorio")
    private String libro;

    @NotBlank(message = "La fecha del préstamo es obligatoria")
    private String fechaPrestamo;
}