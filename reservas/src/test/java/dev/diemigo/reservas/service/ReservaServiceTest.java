package dev.diemigo.reservas.service;

import dev.diemigo.reservas.dto.ReservaDTO;
import dev.diemigo.reservas.exception.ConflictException;
import dev.diemigo.reservas.exception.NotFoundException;
import dev.diemigo.reservas.model.EstadoReserva;
import dev.diemigo.reservas.model.Reserva;
import dev.diemigo.reservas.repository.ReservaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    @DisplayName("Debe registrar una reserva para un libro no disponible")
    void debeRegistrarReservaValida() {
        ReservaDTO entrada = ReservaDTO.builder()
                .libroId(10L)
                .usuarioId(20L)
                .fechaLimiteRetiro(LocalDateTime.now().plusDays(2))
                .disponibleActualmente(false)
                .observacion("Esperando devolucion")
                .build();

        Reserva guardada = Reserva.builder()
                .id(1L)
                .libroId(10L)
                .usuarioId(20L)
                .fechaReserva(LocalDateTime.now())
                .fechaLimiteRetiro(entrada.getFechaLimiteRetiro())
                .disponibleActualmente(false)
                .estado(EstadoReserva.PENDIENTE)
                .observacion("Esperando devolucion")
                .build();

        given(reservaRepository.existsByLibroIdAndUsuarioIdAndEstadoIn(eq(10L), eq(20L), anyCollection()))
                .willReturn(false);
        given(reservaRepository.save(any(Reserva.class))).willReturn(guardada);

        ReservaDTO resultado = reservaService.registrar(entrada);

        assertNotNull(resultado.getId());
        assertEquals(EstadoReserva.PENDIENTE, resultado.getEstado());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    @DisplayName("Debe rechazar una reserva si el libro esta disponible")
    void debeRechazarLibroDisponible() {
        ReservaDTO entrada = ReservaDTO.builder()
                .libroId(10L)
                .usuarioId(20L)
                .fechaLimiteRetiro(LocalDateTime.now().plusDays(2))
                .disponibleActualmente(true)
                .build();

        assertThrows(ConflictException.class, () -> reservaService.registrar(entrada));
    }

    @Test
    @DisplayName("Debe rechazar una reserva duplicada activa")
    void debeRechazarReservaDuplicada() {
        ReservaDTO entrada = ReservaDTO.builder()
                .libroId(10L)
                .usuarioId(20L)
                .fechaLimiteRetiro(LocalDateTime.now().plusDays(2))
                .disponibleActualmente(false)
                .build();

        given(reservaRepository.existsByLibroIdAndUsuarioIdAndEstadoIn(eq(10L), eq(20L), anyCollection()))
                .willReturn(true);

        assertThrows(ConflictException.class, () -> reservaService.registrar(entrada));
    }

    @Test
    @DisplayName("Debe obtener una reserva por su identificador")
    void debeObtenerPorId() {
        Reserva reserva = Reserva.builder()
                .id(1L)
                .libroId(10L)
                .usuarioId(20L)
                .fechaReserva(LocalDateTime.now())
                .fechaLimiteRetiro(LocalDateTime.now().plusDays(2))
                .disponibleActualmente(false)
                .estado(EstadoReserva.PENDIENTE)
                .build();

        given(reservaRepository.findById(1L)).willReturn(Optional.of(reserva));

        ReservaDTO resultado = reservaService.obtenerPorId(1L);

        assertEquals(1L, resultado.getId());
        assertEquals(10L, resultado.getLibroId());
    }

    @Test
    @DisplayName("Debe lanzar excepcion si la reserva no existe")
    void debeLanzarExcepcionSiNoExiste() {
        given(reservaRepository.findById(99L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reservaService.obtenerPorId(99L));
    }

    @Test
    @DisplayName("Debe cancelar una reserva existente")
    void debeCancelarReserva() {
        Reserva reserva = Reserva.builder()
                .id(1L)
                .libroId(10L)
                .usuarioId(20L)
                .fechaReserva(LocalDateTime.now())
                .fechaLimiteRetiro(LocalDateTime.now().plusDays(2))
                .disponibleActualmente(false)
                .estado(EstadoReserva.PENDIENTE)
                .build();

        given(reservaRepository.findById(1L)).willReturn(Optional.of(reserva));
        given(reservaRepository.save(any(Reserva.class))).willAnswer(invocation -> invocation.getArgument(0));

        ReservaDTO resultado = reservaService.cancelar(1L);

        assertEquals(EstadoReserva.CANCELADA, resultado.getEstado());
    }

    @Test
    @DisplayName("Debe listar reservas")
    void debeListarReservas() {
        Reserva reserva = Reserva.builder()
                .id(1L)
                .libroId(10L)
                .usuarioId(20L)
                .fechaReserva(LocalDateTime.now())
                .fechaLimiteRetiro(LocalDateTime.now().plusDays(2))
                .disponibleActualmente(false)
                .estado(EstadoReserva.PENDIENTE)
                .build();

        given(reservaRepository.findAll()).willReturn(List.of(reserva));

        List<ReservaDTO> resultado = reservaService.obtenerTodas();

        assertEquals(1, resultado.size());
    }
}
