package dev.diemigo.dev.auditoria.service;

import dev.diemigo.dev.auditoria.model.RegistroAuditoria;
import dev.diemigo.dev.auditoria.repository.RegistroAuditoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

    @Service
    public class AuditoriaService {

        private static final Logger log = LoggerFactory.getLogger(AuditoriaService.class);

        @Autowired
        private RegistroAuditoriaRepository auditoriaRepository;

        public RegistroAuditoria registrar(RegistroAuditoria registro) {
            registro.setFechaHora(LocalDateTime.now());
            log.debug("Guardando registro de auditoría para servicio: {}", registro.getServicioOrigen());
            RegistroAuditoria guardado = auditoriaRepository.save(registro);
            log.info("Registro de auditoría guardado. ID: {}, Servicio: {}, Resultado: {}",
                    guardado.getId(), guardado.getServicioOrigen(), guardado.getResultado());
            return guardado;
        }

        public List<RegistroAuditoria> obtenerTodos() {
            log.debug("Obteniendo todos los registros de auditoría");
            return auditoriaRepository.findAll();
        }

        public Optional<RegistroAuditoria> obtenerPorId(Long id) {
            log.debug("Buscando registro de auditoría por ID: {}", id);
            return auditoriaRepository.findById(id);
        }

        public List<RegistroAuditoria> obtenerPorServicio(String servicioOrigen) {
            log.debug("Buscando registros por servicio origen: {}", servicioOrigen);
            return auditoriaRepository.findByServicioOrigen(servicioOrigen);
        }

        public List<RegistroAuditoria> obtenerPorResultado(String resultado) {
            log.debug("Buscando registros por resultado: {}", resultado);
            return auditoriaRepository.findByResultado(resultado);
        }

        public void eliminar(Long id) {
            if (!auditoriaRepository.existsById(id)) {
                log.error("Intento de eliminar registro de auditoría inexistente con ID: {}", id);
                throw new EntityNotFoundException("Registro de auditoría no encontrado con ID: " + id);
            }
            auditoriaRepository.deleteById(id);
            log.info("Registro de auditoría eliminado exitosamente. ID: {}", id);
        }
    }