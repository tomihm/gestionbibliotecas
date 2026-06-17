package dev.diemigo.devoluciones.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevolucionDTO {

    @NotNull(message = "El prestamoId es obligatorio")
    private Long prestamoId;

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;
}