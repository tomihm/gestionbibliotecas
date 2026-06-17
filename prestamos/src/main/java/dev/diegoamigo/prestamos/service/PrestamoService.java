package dev.diemigo.prestamos.service;

import dev.diemigo.prestamos.assembler.PrestamoModelAssembler;
import dev.diemigo.prestamos.client.AuditoriaClient;
import dev.diemigo.prestamos.dto.AuditoriaClientDTO;
import dev.diemigo.prestamos.dto.PrestamoDTO;
import dev.diemigo.prestamos.dto.PrestamoRespuestaDTO;
import dev.diemigo.prestamos.model.Prestamo;
import dev.diemigo.prestamos.repository.PrestamoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrestamoService {

    private static final Logger log = LoggerFactory.getLogger(PrestamoService.class);

    private final PrestamoRepository prestamoRepository;
    private final PrestamoModelAssembler assembler;
    private final AuditoriaClient auditoriaClient;

    // Inyección por constructor limpia
    public PrestamoService(PrestamoRepository prestamoRepository, PrestamoModelAssembler assembler, AuditoriaClient auditoriaClient) {
        this.prestamoRepository = prestamoRepository;
        this.assembler = assembler;
        this.auditoriaClient = auditoriaClient;
    }

    @Transactional
    public PrestamoRespuestaDTO crear(PrestamoDTO dto) {
        Prestamo prestamo = new Prestamo();
        prestamo.setNombreCliente(dto.getNombreCliente());
        prestamo.setLibro(dto.getLibro());
        prestamo.setFechaPrestamo(dto.getFechaPrestamo());

        Prestamo guardado = prestamoRepository.save(prestamo);

        // Registro remoto en auditoría
        enviarAuditoria("CREAR_PRESTAMO", "Préstamo creado para el cliente: " + prestamo.getNombreCliente() + " con el libro: " + prestamo.getLibro(), "EXITOSO");

        return assembler.toModel(guardado);
    }

    @Transactional(readOnly = true)
    public List<PrestamoRespuestaDTO> listar() {
        return prestamoRepository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PrestamoRespuestaDTO buscarPorId(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> {
                    enviarAuditoria("BUSCAR_PRESTAMO_FALLIDO", "Intento fallido de buscar préstamo ID: " + id, "FALLIDO");
                    return new EntityNotFoundException("No existe préstamo con ID: " + id);
                });
        return assembler.toModel(prestamo);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!prestamoRepository.existsById(id)) {
            enviarAuditoria("ELIMINAR_PRESTAMO_FALLIDO", "Intento fallido de eliminación. ID no encontrado: " + id, "FALLIDO");
            throw new EntityNotFoundException("No existe préstamo con ID: " + id);
        }
        prestamoRepository.deleteById(id);
        enviarAuditoria("ELIMINAR_PRESTAMO", "Préstamo con ID: " + id + " eliminado permanentemente", "EXITOSO");
    }

    /**
     * Envía de manera segura las trazas al microservicio de Auditoría.
     */
    private void enviarAuditoria(String accion, String detalle, String resultado) {
        try {
            AuditoriaClientDTO logDto = AuditoriaClientDTO.builder()
                    .servicioOrigen("SERVICIO-PRESTAMOS")
                    .accion(accion)
                    .detalle(detalle)
                    .usuarioResponsable("sistema_prestamos")
                    .resultado(resultado)
                    .build();

            auditoriaClient.registrarAccion(logDto);
        } catch (Exception e) {
            log.error("No se pudo conectar con el microservicio de Auditoría: {}", e.getMessage());
        }
    }
}}