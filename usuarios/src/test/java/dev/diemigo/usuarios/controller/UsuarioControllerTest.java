package dev.diemigo.usuarios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.diemigo.usuarios.assembler.UsuarioModelAssembler;
import dev.diemigo.usuarios.dto.UsuarioRespuestaDTO;
import dev.diemigo.usuarios.exception.RequestException;
import dev.diemigo.usuarios.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean //error no es mockbean en la version spring 4
    private UsuarioService usuarioService;

    @MockitoBean
    private UsuarioModelAssembler usuarioModelAssembler;

    @Test
    @DisplayName("Debe retornar 200 al consultar usuario por ID")
    void debeRetornar200CuandoConsultaPorId() throws Exception {

        UsuarioRespuestaDTO dto = UsuarioRespuestaDTO.builder()
                .id(1L)
                .correo("correo@test.cl")
                .build();

        Mockito.when(usuarioService.buscarPorId(1L))
                .thenReturn(dto);

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe retornar 200 al consultar todas los usuarios")
    void debeRetornar200CuandoConsultaTodos() throws Exception {

        UsuarioRespuestaDTO dto = UsuarioRespuestaDTO.builder()
                .id(1L)
                .correo("correo@test.cl")
                .build();

        Mockito.when(usuarioService.listarUsuarios())
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe lanzar RequestException cuando no hay usuarios")
    void debeLanzarExcepcionCuandoListaEstaVacia() {
        Mockito.when(usuarioService.listarUsuarios())
                .thenReturn(List.of());

        assertThrows(RequestException.class, () -> usuarioController().getUsuarios());
    }

    @Test
    @DisplayName("Debe retornar 201 al registrar un usuario")
    void debeRetornar201CuandoRegistra() throws Exception {

        UsuarioRespuestaDTO entrada = UsuarioRespuestaDTO.builder()
                .correo("correo@test.cl")
                .build();

        UsuarioRespuestaDTO salida = UsuarioRespuestaDTO.builder()
                .id(1L)
                .correo("correo@test.cl")
                .build();

        Mockito.when(usuarioService.crearUsuario(any(UsuarioRespuestaDTO.class)))
                .thenReturn(salida);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Debe retornar 204 al eliminar un usuario")
    void debeRetornar204CuandoElimina() throws Exception {
        UsuarioRespuestaDTO dto = UsuarioRespuestaDTO.builder()
                .id(1L)
                .correo("correo@test.cl")
                .build();

        Mockito.when(usuarioService.buscarPorId(1L))
                .thenReturn(dto);

        Mockito.doNothing()
                .when(usuarioService)
                .eliminarUsuario(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe retornar 200 al actualizar usuario")
    void debeRetornar200CuandoActualiza() throws Exception {
        UsuarioRespuestaDTO entrada = UsuarioRespuestaDTO.builder()
                .correo("nuevo@test.cl")
                .build();

        UsuarioRespuestaDTO salida = UsuarioRespuestaDTO.builder()
                .id(1L)
                .correo("nuevo@test.cl")
                .build();

        Mockito.when(usuarioService.actualizarUsuario(any(Long.class), any(UsuarioRespuestaDTO.class)))
                .thenReturn(salida);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk());
    }

    private UsuarioController usuarioController() {
        UsuarioController controller = new UsuarioController();
        org.springframework.test.util.ReflectionTestUtils.setField(controller, "usuarioService", usuarioService);
        org.springframework.test.util.ReflectionTestUtils.setField(controller, "usuarioModelAssembler", usuarioModelAssembler);
        return controller;
    }

}
