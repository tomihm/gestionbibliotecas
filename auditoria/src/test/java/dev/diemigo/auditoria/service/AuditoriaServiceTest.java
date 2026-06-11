package dev.diemigo.auditoria.service;

import dev.diemigo.auditoria.dto.AuditoriaDTO;
import dev.diemigo.auditoria.model.RegistroAuditoria;
import dev.diemigo.auditoria.repository.RegistroAuditoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuditoriaServiceTest {

    @Mock
    private RegistroAuditoriaRepository auditoriaRepository;

    @InjectMocks
    private AuditoriaService auditoriaService;

    @Test
    @DisplayName("Debería persistir y retornar el DTO mapeado correctamente al registrar")
    void debeRegistrarAuditoriaExitosamente() {

        AuditoriaDTO dtoIngreso = AuditoriaDTO.builder()
                .servicioOrigen("SERVICIO-LIBROS")
                .accion("PRESTAMO")
                .detalle("Préstamo de libro id: 4")
                .usuarioResponsable("alumno_duoc")
                .resultado("EXITOSO")
                .build();

        RegistroAuditoria entidadPersistida = new RegistroAuditoria(
                1L,
                "SERVICIO-LIBROS",
                "PRESTAMO",
                "Préstamo de libro id: 4",
                "alumno_duoc",
                LocalDateTime.now(),
                "EXITOSO"
        );

        given(auditoriaRepository.save(any(RegistroAuditoria.class)))
                .willReturn(entidadPersistida);

        AuditoriaDTO resultado = auditoriaService.registrar(dtoIngreso);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("SERVICIO-LIBROS", resultado.getServicioOrigen());

        verify(auditoriaRepository, times(1))
                .save(any(RegistroAuditoria.class));
    }

    @Test
    @DisplayName("Debería retornar los datos mapeados cuando se consulta un ID existente")
    void debeRetornarDtoCuandoIdExiste() {

        Long idBuscado = 5L;

        RegistroAuditoria entidadExistente = new RegistroAuditoria(
                idBuscado,
                "API-GATEWAY",
                "LOGIN",
                "Detalle",
                "root",
                LocalDateTime.now(),
                "EXITOSO"
        );

        given(auditoriaRepository.findById(idBuscado))
                .willReturn(Optional.of(entidadExistente));

        AuditoriaDTO resultado = auditoriaService.obtenerPorId(idBuscado);

        assertNotNull(resultado);
        assertEquals(idBuscado, resultado.getId());
        assertEquals("API-GATEWAY", resultado.getServicioOrigen());

        verify(auditoriaRepository, times(1))
                .findById(idBuscado);
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException si se intenta buscar un ID que no existe")
    void debeLanzarExcepcionCuandoIdNoExiste() {

        Long idErroneo = 999L;

        given(auditoriaRepository.findById(idErroneo))
                .willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                auditoriaService.obtenerPorId(idErroneo));

        verify(auditoriaRepository, times(1))
                .findById(idErroneo);
    }

    @Test
    @DisplayName("Debería eliminar correctamente un registro existente")
    void debeEliminarRegistroExistente() {

        Long id = 1L;

        given(auditoriaRepository.existsById(id))
                .willReturn(true);

        auditoriaService.eliminar(id);

        verify(auditoriaRepository, times(1))
                .existsById(id);

        verify(auditoriaRepository, times(1))
                .deleteById(id);
    }

    @Test
    @DisplayName("Debería lanzar excepción al intentar eliminar un ID inexistente")
    void debeLanzarExcepcionAlEliminarIdInexistente() {

        Long id = 999L;

        given(auditoriaRepository.existsById(id))
                .willReturn(false);

        assertThrows(EntityNotFoundException.class, () ->
                auditoriaService.eliminar(id));

        verify(auditoriaRepository, times(1))
                .existsById(id);
    }
}