package dev.diemigo.devoluciones.service;

import dev.diemigo.devoluciones.assembler.DevolucionModelAssembler;
import dev.diemigo.devoluciones.client.AuditoriaClient;
import dev.diemigo.devoluciones.dto.AuditoriaClientDTO;
import dev.diemigo.devoluciones.dto.DevolucionDTO;
import dev.diemigo.devoluciones.dto.DevolucionRespuestaDTO;
import dev.diemigo.devoluciones.model.Devolucion;
import dev.diemigo.devoluciones.repository.DevolucionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DevolucionService {

    private static final Logger log = LoggerFactory.getLogger(DevolucionService.class);

    private final DevolucionRepository repo;
    private final DevolucionModelAssembler assembler;
    private final AuditoriaClient auditoriaClient;

    // Inyección explícita por constructor, limpio y sin usar @Autowired en atributos
    public DevolucionService(DevolucionRepository repo, DevolucionModelAssembler assembler, AuditoriaClient auditoriaClient) {
        this.repo = repo;
        this.assembler = assembler;
        this.auditoriaClient = auditoriaClient;
    }

    @Transactional
    public DevolucionRespuestaDTO crear(DevolucionDTO dto) {
        Devolucion d = new Devolucion();
        d.setPrestamoId(dto.getPrestamoId());
        d.setUsuarioId(dto.getUsuarioId());
        d.setFechaDevolucionReal(LocalDate.now());
        d.setAtraso(false);

        Devolucion guardada = repo.save(d);

        // Notificación al sistema de auditoría remota
        enviarAuditoria("CREAR_DEVOLUCION", "Procesada devolución para el préstamo ID: " + d.getPrestamoId(), "EXITOSO");

        return assembler.toModel(guardada);
    }

    @Transactional(readOnly = true)
    public List<DevolucionRespuestaDTO> listar() {
        return repo.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DevolucionRespuestaDTO obtener(Long id) {
        Devolucion d = repo.findById(id)
                .orElseThrow(() -> {
                    enviarAuditoria("BUSCAR_DEVOLUCION_FALLIDA", "No se encontró registro de devolución ID: " + id, "FALLIDO");
                    return new EntityNotFoundException("No existe devolución con ID: " + id);
                });
        return assembler.toModel(d);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            enviarAuditoria("ELIMINAR_DEVOLUCION_FALLIDA", "Intento fallido al borrar devolución ID: " + id, "FALLIDO");
            throw new EntityNotFoundException("No existe devolución con ID: " + id);
        }
        repo.deleteById(id);
        enviarAuditoria("ELIMINAR_DEVOLUCION", "Se borró la devolución con ID: " + id, "EXITOSO");
    }

    /**
     * Auxiliar de mensajería con try-catch para proteger el hilo principal
     */
    private void enviarAuditoria(String accion, String detalle, String resultado) {
        try {
            AuditoriaClientDTO logDto = AuditoriaClientDTO.builder()
                    .servicioOrigen("SERVICIO-DEVOLUCIONES")
                    .accion(accion)
                    .detalle(detalle)
                    .usuarioResponsable("sistema_devoluciones")
                    .resultado(resultado)
                    .build();

            auditoriaClient.registrarAccion(logDto);
        } catch (Exception e) {
            log.error("Fallo de red al intentar registrar log en Auditoría: {}", e.getMessage());
        }
    }
}