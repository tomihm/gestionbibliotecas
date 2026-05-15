package dev.diegoamigo.devoluciones.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DevolucionDTO {

    @NotNull(message = "El prestamoId es obligatorio")
    private Long prestamoId;

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;
}