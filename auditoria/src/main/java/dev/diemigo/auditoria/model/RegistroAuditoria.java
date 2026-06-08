package dev.diemigo.auditoria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "registros_auditoria",
        indexes = {
                @Index(name = "idx_servicio_origen", columnList = "servicio_origen"),
                @Index(name = "idx_resultado", columnList = "resultado"),
                @Index(name = "idx_fecha_hora", columnList = "fecha_hora")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "servicio_origen", nullable = false)
    private String servicioOrigen;

    @Column(name = "accion", nullable = false)
    private String accion;

    @Column(columnDefinition = "TEXT")
    private String detalle;

    @Column(name = "usuario_responsable")
    private String usuarioResponsable;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private String resultado;
}