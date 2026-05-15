package dev.diegoamigo.devoluciones.service;

import dev.diegoamigo.devoluciones.dto.DevolucionDTO;
import dev.diegoamigo.devoluciones.model.Devolucion;
import dev.diegoamigo.devoluciones.repository.DevolucionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DevolucionService {

    @Autowired
    private DevolucionRepository repo;

    public Devolucion crear(DevolucionDTO dto) {

        Devolucion d = new Devolucion();

        d.setPrestamoId(dto.getPrestamoId());
        d.setUsuarioId(dto.getUsuarioId());

        d.setFechaDevolucionReal(LocalDate.now());
        d.setAtraso(false);

        return repo.save(d);
    }

    public List<Devolucion> listar() {

        return repo.findAll();
    }

    public Devolucion obtener(Long id) {

        return repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No existe devolución con ID: " + id));
    }

    public void eliminar(Long id) {

        repo.deleteById(id);
    }
}