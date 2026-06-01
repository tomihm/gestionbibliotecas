package dev.diemigo.dev.auditoria.service;

import dev.diemigo.dev.auditoria.dto.AuditoriaDTO;
import dev.diemigo.dev.auditoria.model.RegistroAuditoria;
import dev.diemigo.dev.auditoria.repository.RegistroAuditoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor; //nuevo

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AuditoriaService {


    private static final Logger log = LoggerFactory.getLogger(AuditoriaService.class);

    @Autowired
    private RegistroAuditoriaRepository auditoriaRepository;

    @Transactional
    public AuditoriaDTO registrar(AuditoriaDTO dto) {
        log.debug("Convirtiendo DTO a Entidad para servicio: {}", dto.getServicioOrigen());

        RegistroAuditoria entidad = new RegistroAuditoria();
        entidad.setServicioOrigen(dto.getServicioOrigen());
        entidad.setAccion(dto.getAccion());
        entidad.setDetalle(dto.getDetalle());
        entidad.setUsuarioResponsable(dto.getUsuarioResponsable());
        entidad.setResultado(dto.getResultado());
        entidad.setFechaHora(LocalDateTime.now());

        RegistroAuditoria guardado = auditoriaRepository.save(entidad);
        log.info("Registro de auditoría guardado exitosamente. ID: {}", guardado.getId());

        return convertirADTO(guardado);
    }

    public List<AuditoriaDTO> obtenerTodos() {
        log.debug("Obteniendo todos los registros de auditoría (mapeados a DTO)");
        return auditoriaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public AuditoriaDTO obtenerPorId(Long id) {
        return auditoriaRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new EntityNotFoundException("Registro no encontrado con ID: " + id));
    }

    public void eliminar(Long id) {
        if (!auditoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar: ID " + id + " no existe");
        }
        auditoriaRepository.deleteById(id);
        log.warn("Registro eliminado permanentemente. ID: {}", id);
    }

    private AuditoriaDTO convertirADTO(RegistroAuditoria entidad) {
        return AuditoriaDTO.builder()
                .servicioOrigen(entidad.getServicioOrigen())
                .accion(entidad.getAccion())
                .detalle(entidad.getDetalle())
                .usuarioResponsable(entidad.getUsuarioResponsable())
                .resultado(entidad.getResultado())
                .fechaHora(entidad.getFechaHora())
                .build();
    }
}