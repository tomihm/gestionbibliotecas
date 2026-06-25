package dev.diemigo.inventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.diemigo.inventario.assembler.InventarioModelAssembler;
import dev.diemigo.inventario.dto.InventarioDTO;
import dev.diemigo.inventario.exception.NotFoundException;
import dev.diemigo.inventario.service.InventarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(InventarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private InventarioService inventarioService;

    @MockitoBean
    private InventarioModelAssembler inventarioModelAssembler;

    @Test
    @DisplayName("Debe retornar 200 al consultar inventario por ID")
    void debeRetornar200CuandoConsultaPorId() throws Exception {

        InventarioDTO dto = InventarioDTO.builder()
                .titulo("Harry Potter")
                .estado("Disponible")
                .ubicacion("Estante A")
                .build();

        Mockito.when(inventarioService.buscarPorId(1L))
                .thenReturn(dto);

        Mockito.when(inventarioModelAssembler.toModel(any(InventarioDTO.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(get("/api/inventarios/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe retornar 200 al consultar todos los inventarios")
    void debeRetornar200CuandoConsultaTodos() throws Exception {

        InventarioDTO dto = InventarioDTO.builder()
                .titulo("Harry Potter")
                .estado("Disponible")
                .ubicacion("Estante A")
                .build();

        Mockito.when(inventarioService.listarInventarios())
                .thenReturn(List.of(dto));

        Mockito.when(inventarioModelAssembler.toModel(any(InventarioDTO.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(get("/api/inventarios"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe lanzar NotFoundException cuando no hay inventarios")
    void debeLanzarExcepcionCuandoListaEstaVacia() {

        Mockito.when(inventarioService.listarInventarios())
                .thenReturn(List.of());

        assertThrows(NotFoundException.class,
                () -> inventarioController().getInventarios());
    }

    @Test
    @DisplayName("Debe retornar 201 al registrar un inventario")
    void debeRetornar201CuandoRegistra() throws Exception {

        InventarioDTO entrada = InventarioDTO.builder()
                .titulo("Harry Potter")
                .estado("Disponible")
                .ubicacion("Estante A")
                .build();

        InventarioDTO salida = InventarioDTO.builder()
                .titulo("Harry Potter")
                .estado("Disponible")
                .ubicacion("Estante A")
                .build();

        Mockito.when(inventarioService.crearInventario(any(InventarioDTO.class)))
                .thenReturn(salida);

        Mockito.when(inventarioModelAssembler.toModel(any(InventarioDTO.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/api/inventarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Debe retornar 204 al eliminar un inventario")
    void debeRetornar204CuandoElimina() throws Exception {

        InventarioDTO dto = InventarioDTO.builder()
                .titulo("Harry Potter")
                .estado("Disponible")
                .ubicacion("Estante A")
                .build();

        Mockito.when(inventarioService.buscarPorId(1L))
                .thenReturn(dto);

        Mockito.doNothing()
                .when(inventarioService)
                .eliminarInventario(1L);

        mockMvc.perform(delete("/api/inventarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe retornar 200 al actualizar inventario")
    void debeRetornar200CuandoActualiza() throws Exception {

        InventarioDTO entrada = InventarioDTO.builder()
                .titulo("Harry Potter")
                .estado("Prestado")
                .ubicacion("Estante B")
                .build();

        InventarioDTO salida = InventarioDTO.builder()
                .titulo("Harry Potter")
                .estado("Prestado")
                .ubicacion("Estante B")
                .build();

        Mockito.when(inventarioService.actualizarInventario(any(Long.class), any(InventarioDTO.class)))
                .thenReturn(salida);

        Mockito.when(inventarioModelAssembler.toModel(any(InventarioDTO.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(put("/api/inventarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk());
    }

    private InventarioController inventarioController() {

        InventarioController controller = new InventarioController();

        ReflectionTestUtils.setField(controller,
                "inventarioService",
                inventarioService);

        ReflectionTestUtils.setField(controller,
                "inventarioModelAssembler",
                inventarioModelAssembler);

        return controller;
    }
}
