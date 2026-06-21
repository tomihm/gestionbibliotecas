package dev.notificaciones.controller;

import dev.notificaciones.model.DTO.DTONotificaciones;
import dev.notificaciones.service.NotificacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificacionControllerTest {

    @Mock
    private NotificacionService notificacionService;

    @InjectMocks
    private NotificacionController controller;

    private DTONotificaciones dto;

    @BeforeEach
    void setUp() {
        dto = new DTONotificaciones(new Date(), "Entrega pendiente", "Debe retirar el libro reservado");
    }

    @Test
    void findAllRetornaLista() {
        when(notificacionService.obtenerNotificaciones()).thenReturn(List.of(dto));

        List<DTONotificaciones> resultado = controller.findAll();

        assertEquals(1, resultado.size());
    }

    @Test
    void findByIdRetornaDto() {
        when(notificacionService.getNotificacionesById(1)).thenReturn(dto);

        DTONotificaciones resultado = controller.findById(1);

        assertEquals("Entrega pendiente", resultado.getNotificacion_titulo());
    }

    @Test
    void createNotificacionRetornaDto() {
        when(notificacionService.addNotificacion(dto)).thenReturn(dto);

        DTONotificaciones resultado = controller.createNotificacion(dto);

        assertEquals("Entrega pendiente", resultado.getNotificacion_titulo());
    }

    @Test
    void actualizarNotificacionRetornaDto() {
        when(notificacionService.actualizarNotificacion(1, dto)).thenReturn(dto);

        DTONotificaciones resultado = controller.actualizarNotificacion(1, dto);

        assertEquals("Debe retirar el libro reservado", resultado.getDescripcion());
    }

    @Test
    void deleteByIdInvocaServicio() {
        controller.deleteById(1);

        verify(notificacionService).deleteNotificaciones(1);
    }
}
