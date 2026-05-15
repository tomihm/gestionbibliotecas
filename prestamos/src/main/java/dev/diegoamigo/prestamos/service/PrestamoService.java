package dev.diegoamigo.prestamos.service;

import dev.diegoamigo.prestamos.dto.PrestamoDTO;
import dev.diegoamigo.prestamos.model.Prestamo;
import dev.diegoamigo.prestamos.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository repository;

    public Prestamo crear(PrestamoDTO dto) {

        Prestamo p = new Prestamo();

        p.setNombreCliente(dto.getNombreCliente());
        p.setLibro(dto.getLibro());
        p.setFechaPrestamo(dto.getFechaPrestamo());

        return repository.save(p);
    }

    public List<Prestamo> listar() {

        return repository.findAll();
    }

    public void eliminar(Long id) {

        repository.deleteById(id);
    }
}