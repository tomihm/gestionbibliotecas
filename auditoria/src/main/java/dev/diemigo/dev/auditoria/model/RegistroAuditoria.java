package dev.diemigo.dev.auditoria.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "registros_auditoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El servicio origen no puede estar vacío")
    @Column(nullable = false)
    private String servicioOrigen;

    @NotBlank(message = "La acción no puede estar vacía")
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