package dev.diemigo.libros.service;

import dev.diemigo.libros.dto.LibroDTO;
import dev.diemigo.libros.model.Libro;
import dev.diemigo.libros.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    private LibroDTO convertirADTO(Libro libro) {
        return new LibroDTO(libro.getTitulo(), libro.getAutor());
    }

    public LibroDTO crearLibro(LibroDTO nuevoLibroDTO) {
        Libro libro = new Libro();
        libro.setAutor(nuevoLibroDTO.getAutor());
        libro.setTitulo(nuevoLibroDTO.getTitulo());
        libro.setCategoria("categoria1");
        libro.setIsbn("isbn1");

        Libro guardado = libroRepository.save(libro);
        return convertirADTO(guardado);
    }

    public LibroDTO actualizarLibro(int id, LibroDTO dto) {
        return libroRepository.findById(id)
                .map(libro -> {
                    libro.setTitulo(dto.getTitulo());
                    return convertirADTO(libroRepository.save(libro));
                })
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));
    }

    public List<LibroDTO> listarLibros() {
        return libroRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public LibroDTO buscarPorId(int id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con ID: " + id));
        return convertirADTO(libro);
    }

    public void eliminarLibro(int id) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: El usuario no existe");
        }
        libroRepository.deleteById(id);
    }
}
