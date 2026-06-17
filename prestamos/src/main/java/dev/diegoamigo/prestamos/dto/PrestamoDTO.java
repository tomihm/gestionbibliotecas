package dev.diemigo.prestamos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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