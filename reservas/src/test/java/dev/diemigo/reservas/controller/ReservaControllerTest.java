package dev.diemigo.reservas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.diemigo.reservas.dto.ReservaDTO;
import dev.diemigo.reservas.model.EstadoReserva;
import dev.diemigo.reservas.service.ReservaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ReservaController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReservaService reservaService;

    @Test
    @DisplayName("Debe responder 201 al registrar una reserva")
    void debeResponder201AlRegistrar() throws Exception {
        ReservaDTO entrada = ReservaDTO.builder()
                .libroId(10L)
                .usuarioId(20L)
                .fechaLimiteRetiro(LocalDateTime.now().plusDays(2))
                .disponibleActualmente(false)
                .build();

        ReservaDTO salida = ReservaDTO.builder()
                .id(1L)
                .libroId(10L)
                .usuarioId(20L)
                .fechaReserva(LocalDateTime.now())
                .fechaLimiteRetiro(LocalDateTime.now().plusDays(2))
                .disponibleActualmente(false)
                .estado(EstadoReserva.PENDIENTE)
                .build();

        when(reservaService.registrar(any(ReservaDTO.class))).thenReturn(salida);

        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Debe responder 200 al listar reservas")
    void debeResponder200AlListar() throws Exception {
        when(reservaService.obtenerTodas()).thenReturn(List.of(ReservaDTO.builder().id(1L).build()));

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe responder 200 al obtener una reserva por ID")
    void debeResponder200AlObtenerPorId() throws Exception {
        when(reservaService.obtenerPorId(1L)).thenReturn(ReservaDTO.builder().id(1L).build());

        mockMvc.perform(get("/api/reservas/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe responder 200 al cancelar una reserva")
    void debeResponder200AlCancelar() throws Exception {
        when(reservaService.cancelar(1L)).thenReturn(
                ReservaDTO.builder().id(1L).estado(EstadoReserva.CANCELADA).build()
        );

        mockMvc.perform(patch("/api/reservas/1/cancelar"))
                .andExpect(status().isOk());
    }
}
