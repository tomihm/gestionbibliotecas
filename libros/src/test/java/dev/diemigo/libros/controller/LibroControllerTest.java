package dev.diemigo.libros.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.diemigo.libros.assembler.LibrosModelAssembler;
import dev.diemigo.libros.dto.LibroDTO;
import dev.diemigo.libros.exception.RequestException;
import dev.diemigo.libros.service.LibroService;
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
@WebMvcTest(LibroController.class)
@AutoConfigureMockMvc(addFilters = false)
class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean //error no es mockbean en la version spring 4
    private LibroService libroService;

    @MockitoBean
    private LibrosModelAssembler librosModelAssembler;

    @Test
    @DisplayName("Debe retornar 200 al consultar libro por ID")
    void debeRetornar200CuandoConsultaPorId() throws Exception {

        LibroDTO dto = LibroDTO.builder()
                .id(1L)
                .titulo("Harry Potter")
                .autor("J.K. Rowling")
                .build();

        Mockito.when(libroService.buscarPorId(1L))
                .thenReturn(dto);

        mockMvc.perform(get("/api/libros/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe retornar 200 al consultar todas los libros")
    void debeRetornar200CuandoConsultaTodos() throws Exception {

        LibroDTO dto = LibroDTO.builder()
                .id(1L)
                .titulo("Harry Potter")
                .autor("J.K. Rowling")
                .build();

        Mockito.when(libroService.listarLibros())
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/libros"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe lanzar RequestException cuando no hay libros")
    void debeLanzarExcepcionCuandoListaEstaVacia() {
        Mockito.when(libroService.listarLibros())
                .thenReturn(List.of());

        assertThrows(RequestException.class, () -> libroController().getLibros());
    }

    @Test
    @DisplayName("Debe retornar 201 al registrar un libro")
    void debeRetornar201CuandoRegistra() throws Exception {

        LibroDTO entrada = LibroDTO.builder()
                .titulo("Harry Potter")
                .autor("J.K. Rowling")
                .build();

        LibroDTO salida = LibroDTO.builder()
                .id(1L)
                .titulo("Harry Potter")
                .autor("J.K. Rowling")
                .build();

        Mockito.when(libroService.crearLibro(any(LibroDTO.class)))
                .thenReturn(salida);

        mockMvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Debe retornar 204 al eliminar un libro")
    void debeRetornar204CuandoElimina() throws Exception {
        LibroDTO dto = LibroDTO.builder()
                .id(1L)
                .titulo("Harry Potter")
                .autor("J.K. Rowling")
                .build();

        Mockito.when(libroService.buscarPorId(1L))
                .thenReturn(dto);

        Mockito.doNothing()
                .when(libroService)
                .eliminarLibro(1L);

        mockMvc.perform(delete("/api/libros/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe retornar 200 al actualizar libro")
    void debeRetornar200CuandoActualiza() throws Exception {
        LibroDTO entrada = LibroDTO.builder()
                .titulo("Nuevo Titulo")
                .autor("Nuevo Autor")
                .build();

        LibroDTO salida = LibroDTO.builder()
                .id(1L)
                .titulo("Nuevo Titulo")
                .autor("Nuevo Autor")
                .build();

        Mockito.when(libroService.actualizarLibro(any(Long.class), any(LibroDTO.class)))
                .thenReturn(salida);

        mockMvc.perform(put("/api/libros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk());
    }

    private LibroController libroController() {
        LibroController controller = new LibroController();
        org.springframework.test.util.ReflectionTestUtils.setField(controller, "LibroService", libroService);
        org.springframework.test.util.ReflectionTestUtils.setField(controller, "LibrosModelAssembler", librosModelAssembler);
        return controller;
    }

}
