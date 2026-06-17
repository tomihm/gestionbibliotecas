package dev.diemigo.reservas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libro_id", nullable = false)
    private Long libroId;

    @NotNull
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @NotNull
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    @NotNull
    @Column(name = "fecha_limite_retiro", nullable = false)
    private LocalDateTime fechaLimiteRetiro;

    @Column(name = "disponible_actualmente", nullable = false)
    private Boolean disponibleActualmente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EstadoReserva estado;

    @Column(length = 255)
    private String observacion;
}
