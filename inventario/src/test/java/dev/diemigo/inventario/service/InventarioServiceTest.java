package dev.diemigo.inventario.service;


import dev.diemigo.inventario.dto.InventarioDTO;
import dev.diemigo.inventario.exception.NotFoundException;
import dev.diemigo.inventario.model.Inventario;
import dev.diemigo.inventario.repository.InventarioRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void crearInventarioPersisteCorrectamente() {

        InventarioDTO request = InventarioDTO.builder()
                .titulo("Clean Code")
                .estado("Disponible")
                .ubicacion("Estante A")
                .build();

        Inventario guardado = new Inventario(
                1L,
                100L,
                "Clean Code",
                "Disponible",
                "Estante A"
        );

        when(inventarioRepository.save(any(Inventario.class)))
                .thenReturn(guardado);

        InventarioDTO resultado = inventarioService.crearInventario(request);

        assertEquals("Clean Code", resultado.getTitulo());
        assertEquals("Disponible", resultado.getEstado());
        assertEquals("Estante A", resultado.getUbicacion());

        verify(inventarioRepository).save(any(Inventario.class));
    }

    @Test
    void listarInventariosRetornaDtos() {

        Inventario inventario = new Inventario(
                1L,
                100L,
                "Clean Code",
                "Disponible",
                "Estante A"
        );

        when(inventarioRepository.findAll())
                .thenReturn(List.of(inventario));

        List<InventarioDTO> resultado = inventarioService.listarInventarios();

        assertEquals(1, resultado.size());
        assertEquals("Clean Code", resultado.get(0).getTitulo());
        assertEquals("Disponible", resultado.get(0).getEstado());
        assertEquals("Estante A", resultado.get(0).getUbicacion());
    }

    @Test
    void buscarPorIdRetornaInventarioExistente() {

        Inventario inventario = new Inventario(
                1L,
                100L,
                "Clean Code",
                "Disponible",
                "Estante A"
        );

        when(inventarioRepository.findById(1L))
                .thenReturn(Optional.of(inventario));

        InventarioDTO resultado = inventarioService.buscarPorId(1L);

        assertEquals("Clean Code", resultado.getTitulo());
        assertEquals("Disponible", resultado.getEstado());
        assertEquals("Estante A", resultado.getUbicacion());
    }

    @Test
    void buscarPorIdLanzaErrorSiNoExiste() {

        when(inventarioRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> inventarioService.buscarPorId(99L));
    }

    @Test
    void actualizarInventarioModificaDatosSiExiste() {

        Inventario inventario = new Inventario(
                1L,
                100L,
                "Libro Viejo",
                "Prestado",
                "Bodega"
        );

        Inventario actualizado = new Inventario(
                1L,
                100L,
                "Libro Nuevo",
                "Disponible",
                "Estante B"
        );

        InventarioDTO dto = InventarioDTO.builder()
                .titulo("Libro Nuevo")
                .estado("Disponible")
                .ubicacion("Estante B")
                .build();

        when(inventarioRepository.findById(1L))
                .thenReturn(Optional.of(inventario));

        when(inventarioRepository.save(any(Inventario.class)))
                .thenReturn(actualizado);

        InventarioDTO resultado = inventarioService.actualizarInventario(1L, dto);

        assertEquals("Libro Nuevo", resultado.getTitulo());
        assertEquals("Disponible", resultado.getEstado());
        assertEquals("Estante B", resultado.getUbicacion());
    }

    @Test
    void actualizarInventarioLanzaErrorSiNoExiste() {

        when(inventarioRepository.findById(99L))
                .thenReturn(Optional.empty());

        InventarioDTO dto = InventarioDTO.builder()
                .titulo("Libro")
                .estado("Disponible")
                .ubicacion("Estante")
                .build();

        assertThrows(RuntimeException.class,
                () -> inventarioService.actualizarInventario(99L, dto));
    }

    @Test
    void eliminarInventarioBorraSiExiste() {

        when(inventarioRepository.existsById(1L))
                .thenReturn(true);

        inventarioService.eliminarInventario(1L);

        verify(inventarioRepository).deleteById(1L);
    }

    @Test
    void eliminarInventarioLanzaErrorSiNoExiste() {

        when(inventarioRepository.existsById(99L))
                .thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> inventarioService.eliminarInventario(99L));
    }
}