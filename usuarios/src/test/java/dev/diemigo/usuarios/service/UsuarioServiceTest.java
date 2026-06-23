package dev.diemigo.usuarios.service;

import dev.diemigo.usuarios.dto.UsuarioRespuestaDTO;
import dev.diemigo.usuarios.exception.NotFoundException;
import dev.diemigo.usuarios.model.Usuario;
import dev.diemigo.usuarios.model.UsuarioRol;
import dev.diemigo.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void crearUsuarioAsignaRolYPersiste() {
        UsuarioRespuestaDTO request = UsuarioRespuestaDTO.builder()
                .correo("usuario@test.cl")
                .build();
        Usuario guardado = new Usuario(1L, "usuario@test.cl", "password123", true, UsuarioRol.USUARIO);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(guardado);

        UsuarioRespuestaDTO resultado = usuarioService.crearUsuario(request);

        assertEquals(1L, resultado.getId());
        assertEquals("usuario@test.cl", resultado.getCorreo());
        verify(usuarioRepository).save(argThat(usuario -> usuario.getRol() == UsuarioRol.USUARIO));
    }

    @Test
    void listarUsuariosRetornaDtos() {
        Usuario usuario = new Usuario(1L, "usuario@test.cl", "password123", true, UsuarioRol.USUARIO);
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioRespuestaDTO> resultado = usuarioService.listarUsuarios();

        assertEquals(1, resultado.size());
        assertEquals("usuario@test.cl", resultado.get(0).getCorreo());
    }

    @Test
    void buscarPorIdRetornaUsuarioExistente() {
        Usuario usuario = new Usuario(1L, "usuario@test.cl", "password123", true, UsuarioRol.USUARIO);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        assertEquals(1L, usuarioService.buscarPorId(1L).getId());
    }

    @Test
    void buscarPorIdLanzaErrorSiNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usuarioService.buscarPorId(99L));
    }

    @Test
    void actualizarUsuarioModificaCorreoSiExiste() {
        Usuario usuario = new Usuario(1L, "antiguo@test.cl", "password123", true, UsuarioRol.USUARIO);
        Usuario actualizado = new Usuario(1L, "nuevo@test.cl", "password123", true, UsuarioRol.USUARIO);
        UsuarioRespuestaDTO dto = UsuarioRespuestaDTO.builder().correo("nuevo@test.cl").build();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(actualizado);

        UsuarioRespuestaDTO resultado = usuarioService.actualizarUsuario(1L, dto);

        assertEquals("nuevo@test.cl", resultado.getCorreo());
    }

    @Test
    void actualizarUsuarioLanzaErrorSiNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                usuarioService.actualizarUsuario(99L, UsuarioRespuestaDTO.builder().correo("nuevo@test.cl").build()));
    }

    @Test
    void eliminarUsuarioBorraSiExiste() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        usuarioService.eliminarUsuario(1L);

        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void eliminarUsuarioLanzaErrorSiNoExiste() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> usuarioService.eliminarUsuario(99L));
    }
}
