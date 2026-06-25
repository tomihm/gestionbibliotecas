package dev.diemigo.libros.service;


import dev.diemigo.libros.dto.LibroDTO;
import dev.diemigo.libros.model.Libro;
import dev.diemigo.libros.repository.LibroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroService libroService;

    @Test
    void crearLibroPersisteCorrectamente() {

        LibroDTO request = LibroDTO.builder()
                .titulo("Codigo limpio")
                .autor("Autor 1")
                .build();

        Libro guardado = new Libro(
                1L,
                "Codigo limpio",
                "Autor 1",
                "categoria1",
                "isbn1"
        );

        when(libroRepository.save(any(Libro.class)))
                .thenReturn(guardado);

        LibroDTO resultado = libroService.crearLibro(request);

        assertEquals("Codigo limpio", resultado.getTitulo());
        assertEquals("Autor 1", resultado.getAutor());

        verify(libroRepository).save(any(Libro.class));
    }

    @Test
    void listarLibrosRetornaDtos() {

        Libro libro = new Libro(
                1L,
                "Codigo limpio",
                "Autor 1",
                "categoria1",
                "isbn1"
        );

        when(libroRepository.findAll())
                .thenReturn(List.of(libro));

        List<LibroDTO> resultado = libroService.listarLibros();

        assertEquals(1, resultado.size());
        assertEquals("Codigo limpio", resultado.get(0).getTitulo());
        assertEquals("Autor 1", resultado.get(0).getAutor());
    }

    @Test
    void buscarPorIdRetornaLibroExistente() {

        Libro libro = new Libro(
                1L,
                "Codigo limpio",
                "Autor 1",
                "categoria1",
                "isbn1"
        );

        when(libroRepository.findById(1L))
                .thenReturn(Optional.of(libro));

        LibroDTO resultado = libroService.buscarPorId(1L);

        assertEquals("Codigo limpio", resultado.getTitulo());
        assertEquals("Autor 1", resultado.getAutor());
    }

    @Test
    void buscarPorIdLanzaErrorSiNoExiste() {

        when(libroRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> libroService.buscarPorId(99L));
    }

    @Test
    void actualizarLibroModificaTituloSiExiste() {

        Libro libro = new Libro(
                1L,
                "Java Básico",
                "Autor X",
                "categoria1",
                "isbn1"
        );

        Libro actualizado = new Libro(
                1L,
                "Java Avanzado",
                "Autor X",
                "categoria1",
                "isbn1"
        );

        LibroDTO dto = LibroDTO.builder()
                .titulo("Java Avanzado")
                .autor("Autor X")
                .build();

        when(libroRepository.findById(1L))
                .thenReturn(Optional.of(libro));

        when(libroRepository.save(any(Libro.class)))
                .thenReturn(actualizado);

        LibroDTO resultado = libroService.actualizarLibro(1L, dto);

        assertEquals("Java Avanzado", resultado.getTitulo());
        assertEquals("Autor X", resultado.getAutor());
    }

    @Test
    void actualizarLibroLanzaErrorSiNoExiste() {

        when(libroRepository.findById(99L))
                .thenReturn(Optional.empty());

        LibroDTO dto = LibroDTO.builder()
                .titulo("Nuevo Libro")
                .autor("Autor")
                .build();

        assertThrows(RuntimeException.class,
                () -> libroService.actualizarLibro(99L, dto));
    }

    @Test
    void eliminarLibroBorraSiExiste() {

        when(libroRepository.existsById(1L))
                .thenReturn(true);

        libroService.eliminarLibro(1L);

        verify(libroRepository).deleteById(1L);
    }

    @Test
    void eliminarLibroLanzaErrorSiNoExiste() {

        when(libroRepository.existsById(99L))
                .thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> libroService.eliminarLibro(99L));
    }
}