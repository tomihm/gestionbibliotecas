package data.notificaciones.model.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DTONotificaciones {

    @NotNull
    private Date dia_entrega;

    @NotNull
    private String notificacion_titulo;

    @NotNull
    private String descripcion;

}
