package dev.notificaciones.service;

import dev.notificaciones.model.DTO.DTONotificaciones;
import dev.notificaciones.model.Notificacion;
import dev.notificaciones.repository.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository repositoryNotificacion;

    @InjectMocks
    private NotificacionService service;

    private Notificacion notificacion;
    private Date fecha;

    @BeforeEach
    void setUp() {
        fecha = new Date();
        notificacion = new Notificacion();
        notificacion.setId(1);
        notificacion.setDia_entrega(fecha);
        notificacion.setNotificacion_titulo("Entrega pendiente");
        notificacion.setDescripcion("Debe retirar el libro reservado");
    }

    @Test
    void obtenerNotificacionesRetornaDtos() {
        when(repositoryNotificacion.findAll()).thenReturn(List.of(notificacion));

        List<DTONotificaciones> resultado = service.obtenerNotificaciones();

        assertEquals(1, resultado.size());
        assertEquals("Entrega pendiente", resultado.getFirst().getNotificacion_titulo());
    }

    @Test
    void getNotificacionesByIdRetornaDto() {
        when(repositoryNotificacion.findById(1)).thenReturn(Optional.of(notificacion));

        DTONotificaciones resultado = service.getNotificacionesById(1);

        assertEquals("Entrega pendiente", resultado.getNotificacion_titulo());
    }

    @Test
    void addNotificacionGuardaEntidad() {
        DTONotificaciones dto = new DTONotificaciones(fecha, "Entrega pendiente", "Debe retirar el libro reservado");
        when(repositoryNotificacion.save(org.mockito.ArgumentMatchers.any(Notificacion.class))).thenReturn(notificacion);

        DTONotificaciones resultado = service.addNotificacion(dto);

        assertEquals("Entrega pendiente", resultado.getNotificacion_titulo());
    }

    @Test
    void actualizarNotificacionActualizaCampos() {
        DTONotificaciones dto = new DTONotificaciones(fecha, "Entrega actualizada", "Nueva descripcion");
        when(repositoryNotificacion.findById(1)).thenReturn(Optional.of(notificacion));
        when(repositoryNotificacion.save(notificacion)).thenReturn(notificacion);

        DTONotificaciones resultado = service.actualizarNotificacion(1, dto);

        assertEquals("Entrega actualizada", resultado.getNotificacion_titulo());
        assertEquals("Nueva descripcion", resultado.getDescripcion());
    }

    @Test
    void deleteNotificacionesEliminaPorId() {
        service.deleteNotificaciones(1);

        verify(repositoryNotificacion).deleteById(1);
    }
}
