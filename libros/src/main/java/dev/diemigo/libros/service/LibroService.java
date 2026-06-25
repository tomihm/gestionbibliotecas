package dev.diemigo.libros.service;

import dev.diemigo.libros.dto.LibroDTO;
import dev.diemigo.libros.exception.NotFoundException;
import dev.diemigo.libros.model.Libro;
import dev.diemigo.libros.repository.LibroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    private static final Logger log = LoggerFactory.getLogger(LibroService.class);

    private LibroDTO convertirADTO(Libro libro) {
        return new LibroDTO(libro.getTitulo(), libro.getAutor());
    }

    @Transactional
    public LibroDTO crearLibro(LibroDTO nuevoLibroDTO) {

        log.debug("Creando libro en la base de datos: {}", nuevoLibroDTO);

        Libro libro = new Libro();
        libro.setTitulo(nuevoLibroDTO.getTitulo());
        libro.setAutor(nuevoLibroDTO.getAutor());

        Libro guardado = libroRepository.save(libro);

        log.info("Libro guardado en la base de datos: {}", guardado);
        return convertirADTO(guardado);
    }

    @Transactional
    public LibroDTO actualizarLibro(Long id, LibroDTO dto) {
        log.debug("Actualizando libro en la base de datos: {}", dto);
        return libroRepository.findById(id)
                .map(libro -> {
                    libro.setTitulo(dto.getTitulo());
                    libro.setAutor(dto.getAutor());
                    log.info("Libro actualizado en la base de datos: {}", id);
                    return convertirADTO(libroRepository.save(libro));
                })
                .orElseThrow(() -> new RuntimeException("libro con ID " + id + " no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<LibroDTO> listarLibros() {

        log.debug("Listando Libros en la base de datos");
        return libroRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LibroDTO buscarPorId(Long id) {

        log.debug("Buscando libro en la base de datos: {}", id);
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el libro con ID: " + id));

        log.info("libro encontrado en la base de datos: {}", libro);
        return convertirADTO(libro);
    }

    public void eliminarLibro(Long id) {

        log.debug("Eliminando libro en la base de datos: {}", id);

        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: El libro no existe");
        }

        log.info("Libro eliminado en la base de datos: {}", id);
        libroRepository.deleteById(id);
    }
}