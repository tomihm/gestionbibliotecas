package dev.diemigo.reservas.service;

import dev.diemigo.reservas.dto.ReservaDTO;
import dev.diemigo.reservas.exception.ConflictException;
import dev.diemigo.reservas.exception.NotFoundException;
import dev.diemigo.reservas.model.EstadoReserva;
import dev.diemigo.reservas.model.Reserva;
import dev.diemigo.reservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservaService {

    private static final Set<EstadoReserva> ESTADOS_ACTIVOS = Set.of(
            EstadoReserva.PENDIENTE,
            EstadoReserva.NOTIFICADA
    );

    private final ReservaRepository reservaRepository;

    @Transactional
    public ReservaDTO registrar(ReservaDTO dto) {
        if (Boolean.TRUE.equals(dto.getDisponibleActualmente())) {
            throw new ConflictException("Solo se pueden reservar libros que no estan disponibles actualmente");
        }

        if (reservaRepository.existsByLibroIdAndUsuarioIdAndEstadoIn(
                dto.getLibroId(),
                dto.getUsuarioId(),
                ESTADOS_ACTIVOS
        )) {
            throw new ConflictException("Ya existe una reserva activa para este libro y usuario");
        }

        Reserva reserva = Reserva.builder()
                .libroId(dto.getLibroId())
                .usuarioId(dto.getUsuarioId())
                .fechaReserva(LocalDateTime.now())
                .fechaLimiteRetiro(dto.getFechaLimiteRetiro())
                .disponibleActualmente(dto.getDisponibleActualmente())
                .estado(EstadoReserva.PENDIENTE)
                .observacion(dto.getObservacion())
                .build();

        return toDto(reservaRepository.save(reserva));
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> obtenerTodas() {
        return reservaRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReservaDTO obtenerPorId(Long id) {
        return reservaRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada con ID: " + id));
    }

    @Transactional
    public ReservaDTO cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada con ID: " + id));

        reserva.setEstado(EstadoReserva.CANCELADA);
        return toDto(reservaRepository.save(reserva));
    }

    private ReservaDTO toDto(Reserva reserva) {
        return ReservaDTO.builder()
                .id(reserva.getId())
                .libroId(reserva.getLibroId())
                .usuarioId(reserva.getUsuarioId())
                .fechaReserva(reserva.getFechaReserva())
                .fechaLimiteRetiro(reserva.getFechaLimiteRetiro())
                .disponibleActualmente(reserva.getDisponibleActualmente())
                .estado(reserva.getEstado())
                .observacion(reserva.getObservacion())
                .build();
    }
}
