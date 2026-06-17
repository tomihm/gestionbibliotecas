package dev.diemigo.devoluciones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaClientDTO {
    private String servicioOrigen;
    private String accion;
    private String detalle;
    private String usuarioResponsable;
    private String resultado;
}