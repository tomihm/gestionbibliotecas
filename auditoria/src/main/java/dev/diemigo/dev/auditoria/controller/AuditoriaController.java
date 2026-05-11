package dev.diemigo.dev.auditoria.controller;


import dev.diemigo.dev.auditoria.dto.ErrorResponse;
import dev.diemigo.dev.auditoria.model.RegistroAuditoria;
import dev.diemigo.dev.auditoria.service.AuditoriaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {

    private static final Logger log = LoggerFactory.getLogger(AuditoriaController.class);

    @Autowired
    private AuditoriaService auditoriaService;

    @PostMapping
    public ResponseEntity<RegistroAuditoria> registrar(@Valid @RequestBody RegistroAuditoria registro) {
        log.info("Registrando auditoría para servicio: {}", registro.getServicioOrigen());
        RegistroAuditoria nuevo = auditoriaService.registrar(registro);
        log.info("Auditoría registrada exitosamente con ID: {}", nuevo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping
    public ResponseEntity<List<RegistroAuditoria>> obtenerTodos() {
        log.info("Obteniendo todos los registros de auditoría");
        List<RegistroAuditoria> registros = auditoriaService.obtenerTodos();
        log.info("Registros encontrados: {}", registros.size());
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroAuditoria> obtenerPorId(@PathVariable Long id) {
        log.info("Buscando registro de auditoría con ID: {}", id);
        return auditoriaService.obtenerPorId(id)
                .map(registro -> {
                    log.info("Registro encontrado con ID: {}", id);
                    return ResponseEntity.ok(registro);
                })
                .orElseGet(() -> {
                    log.warn("Registro de auditoría no encontrado con ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/servicio/{servicioOrigen}")
    public ResponseEntity<List<RegistroAuditoria>> obtenerPorServicio(
            @PathVariable String servicioOrigen) {
        log.info("Buscando registros por servicio origen: {}", servicioOrigen);
        List<RegistroAuditoria> registros = auditoriaService.obtenerPorServicio(servicioOrigen);
        log.info("Registros encontrados para servicio {}: {}", servicioOrigen, registros.size());
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/resultado/{resultado}")
    public ResponseEntity<List<RegistroAuditoria>> obtenerPorResultado(
            @PathVariable String resultado) {
        log.info("Buscando registros por resultado: {}", resultado);
        List<RegistroAuditoria> registros = auditoriaService.obtenerPorResultado(resultado);
        log.info("Registros encontrados para resultado {}: {}", resultado, registros.size());
        return ResponseEntity.ok(registros);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.warn("Eliminando registro de auditoría con ID: {}", id);
        auditoriaService.eliminar(id);
        log.info("Registro de auditoría eliminado con ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}