package dev.diegoamigo.multas.service;

import dev.diegoamigo.multas.dto.MultaDTO;
import dev.diegoamigo.multas.model.Multa;
import dev.diegoamigo.multas.repository.MultaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MultaService {

    private final MultaRepository repository;

    public MultaService(MultaRepository repository) {
        this.repository = repository;
    }

    public List<Multa> listar() {
        return repository.findAll();
    }

    public Multa guardar(MultaDTO dto) {

        Multa multa = new Multa();

        multa.setPrestamoId(dto.getPrestamoId());
        multa.setMonto(dto.getMonto());
        multa.setMotivo(dto.getMotivo());
        multa.setPagada(dto.isPagada());

        return repository.save(multa);
    }

    public Multa obtener(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No existe multa con ID: " + id));
    }

    public void eliminar(Long id) {

        repository.deleteById(id);
    }
}