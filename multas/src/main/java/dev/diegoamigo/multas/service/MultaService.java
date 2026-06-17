package dev.diegoamigo.multas.service;

import dev.diegoamigo.multas.assembler.MultaAssembler;
import dev.diegoamigo.multas.client.AuditoriaClient;
import dev.diegoamigo.multas.dto.AuditoriaClientDTO;
import dev.diegoamigo.multas.dto.MultaDTO;
import dev.diegoamigo.multas.dto.MultaRespuestaDTO;
import dev.diegoamigo.multas.model.Multa;
import dev.diegoamigo.multas.repository.MultaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MultaService {

    private static final Logger log = LoggerFactory.getLogger(MultaService.class);

    private final MultaRepository repository;
    private final MultaAssembler assembler;
    private final AuditoriaClient auditoriaClient;

    public MultaService(MultaRepository repository, MultaAssembler assembler, AuditoriaClient auditoriaClient) {
        this.repository = repository;
        this.assembler = assembler;
        this.auditoriaClient = auditoriaClient;
    }

    @Transactional(readOnly = true)
    public List<MultaRespuestaDTO> listar() {
        return repository.findAll()
                .stream()
                .map(assembler::toModel) // Usamos el assembler HATEOAS
                .collect(Collectors.toList());
    }

    @Transactional
    public MultaRespuestaDTO guardar(MultaDTO dto) {
        Multa multa = new Multa();
        multa.setPrestamoId(dto.getPrestamoId());
        multa.setMonto(dto.getMonto());
        multa.setMotivo(dto.getMotivo());
        multa.setPagada(dto.isPagada());

        Multa guardada = repository.save(multa);

        // Ejecutar trigger de auditoría asíncrono/remoto
        notificarAuditoria("CREAR_MULTA", "Se generó multa de valor " + multa.getMonto() + " asociada al préstamo " + multa.getPrestamoId(), "EXITOSO");

        return assembler.toModel(guardada);
    }

    @Transactional(readOnly = true)
    public MultaRespuestaDTO obtener(Long id) {
        Multa multa = repository.findById(id)
                .orElseThrow(() -> {
                    notificarAuditoria("BUSCAR_MULTA", "Intento fallido de lectura de multa ID: " + id, "FALLIDO");
                    return new EntityNotFoundException("No existe multa con ID: " + id);
                });
        return assembler.toModel(multa);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            notificarAuditoria("ELIMINAR_MULTA", "Intento fallido de borrado de multa ID: " + id + " (No existe)", "FALLIDO");
            throw new EntityNotFoundException("No existe multa con ID: " + id);
        }

        repository.deleteById(id);
        notificarAuditoria("ELIMINAR_MULTA", "Se eliminó permanentemente la multa ID: " + id, "EXITOSO");
    }

    /**
     * Envía la bitácora de operaciones del microservicio de multas hacia auditoría.
     * Envuelta en un try-catch para no interrumpir el flujo del usuario principal si el microservicio destino cae.
     */
    private void notificarAuditoria(String accion, String detalle, String resultado) {
        try {
            AuditoriaClientDTO auditoriaLog = AuditoriaClientDTO.builder()
                    .servicioOrigen("SERVICIO-MULTAS")
                    .accion(accion)
                    .detalle(detalle)
                    .usuarioResponsable("sistema_multas_feign")
                    .resultado(resultado)
                    .build();

            auditoriaClient.registrarAccion(auditoriaLog);
        } catch (Exception e) {
            log.error("Error crítico de comunicación con el Microservicio de Auditoría: {}", e.getMessage());
        }
    }
}