package dev.diegoamigo.multas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultaDTO {

    @NotNull(message = "El prestamoId es obligatorio")
    private Long prestamoId;

    @Min(value = 1, message = "El monto debe ser mayor a 0")
    private double monto;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    private boolean pagada;
}