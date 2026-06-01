package dev.diemigo.dev.auditoria.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "RegistrosAuditoria",
        indexes = {
        @Index(name = "idx_servicio_origen", columnList = "servicio_origen"),
        @Index(name= "idx_resultado", columnList = "resultado"),
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

    @Column(nullable = false)
    private String servicioOrigen;

    @Column(nullable = false)
    private String accion;

    @Column(columnDefinition = "TEXT")
    private String detalle;

    private String usuarioResponsable;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private String resultado;
}