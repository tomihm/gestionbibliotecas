package dev.diemigo.reservas.dto;

import dev.diemigo.reservas.model.EstadoReserva;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Long id;

    @NotNull(message = "El identificador del libro es obligatorio")
    private Long libroId;

    @NotNull(message = "El identificador del usuario es obligatorio")
    private Long usuarioId;

    private LocalDateTime fechaReserva;

    @NotNull(message = "La fecha limite de retiro es obligatoria")
    @Future(message = "La fecha limite de retiro debe estar en el futuro")
    private LocalDateTime fechaLimiteRetiro;

    @NotNull(message = "Debe indicar si el libro esta disponible actualmente")
    private Boolean disponibleActualmente;

    private EstadoReserva estado;

    private String observacion;
}
