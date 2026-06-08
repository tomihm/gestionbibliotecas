package dev.diemigo.auditoria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.diemigo.dev.auditoria.dto.AuditoriaDTO;
import dev.diemigo.dev.auditoria.service.AuditoriaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuditoriaController.class)
class AuditoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuditoriaService auditoriaService;

    @Test
    @DisplayName("Debe retornar 200 al consultar auditoría por ID")
    void debeRetornar200CuandoConsultaPorId() throws Exception {

        AuditoriaDTO dto = AuditoriaDTO.builder()
                .id(1L)
                .servicioOrigen("SERVICIO-LIBROS")
                .accion("CONSULTA")
                .resultado("EXITOSO")
                .fechaHora(LocalDateTime.now())
                .build();

        Mockito.when(auditoriaService.obtenerPorId(1L))
                .thenReturn(dto);

        mockMvc.perform(get("/api/auditoria/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe retornar 200 al consultar todas las auditorías")
    void debeRetornar200CuandoConsultaTodos() throws Exception {

        AuditoriaDTO dto = AuditoriaDTO.builder()
                .id(1L)
                .servicioOrigen("SERVICIO-LIBROS")
                .accion("CONSULTA")
                .resultado("EXITOSO")
                .fechaHora(LocalDateTime.now())
                .build();

        Mockito.when(auditoriaService.obtenerTodos())
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/auditoria"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe retornar 201 al registrar auditoría")
    void debeRetornar201CuandoRegistra() throws Exception {

        AuditoriaDTO entrada = AuditoriaDTO.builder()
                .servicioOrigen("SERVICIO-LIBROS")
                .accion("CREAR")
                .resultado("EXITOSO")
                .build();

        AuditoriaDTO salida = AuditoriaDTO.builder()
                .id(1L)
                .servicioOrigen("SERVICIO-LIBROS")
                .accion("CREAR")
                .resultado("EXITOSO")
                .fechaHora(LocalDateTime.now())
                .build();

        Mockito.when(auditoriaService.registrar(any(AuditoriaDTO.class)))
                .thenReturn(salida);

        mockMvc.perform(post("/api/auditoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Debe retornar 204 al eliminar auditoría")
    void debeRetornar204CuandoElimina() throws Exception {

        Mockito.doNothing()
                .when(auditoriaService)
                .eliminar(1L);

        mockMvc.perform(delete("/api/auditoria/1"))
                .andExpect(status().isNoContent());
    }
}