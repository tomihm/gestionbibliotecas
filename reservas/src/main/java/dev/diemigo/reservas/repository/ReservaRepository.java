package dev.diemigo.reservas.repository;

import dev.diemigo.reservas.model.EstadoReserva;
import dev.diemigo.reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsByLibroIdAndUsuarioIdAndEstadoIn(Long libroId, Long usuarioId, Collection<EstadoReserva> estados);
}
