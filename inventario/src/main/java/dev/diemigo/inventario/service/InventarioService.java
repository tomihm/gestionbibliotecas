package dev.diemigo.inventario.service;

import dev.diemigo.inventario.dto.InventarioDTO;
import dev.diemigo.inventario.exception.NotFoundException;
import dev.diemigo.inventario.model.Inventario;
import dev.diemigo.inventario.repository.InventarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    private static final Logger log = LoggerFactory.getLogger(InventarioService.class);

    private InventarioDTO convertirADTO(Inventario inventario) {
        return new InventarioDTO(inventario.getTitulo(),inventario.getEstado(),inventario.getUbicacion());
    }

    @Transactional
    public InventarioDTO crearInventario(InventarioDTO nuevoInventarioDTO) {

        log.debug("Creando inventario en la base de datos: {}", nuevoInventarioDTO);

        Inventario inventario = new Inventario();
        inventario.setTitulo(nuevoInventarioDTO.getTitulo());
        inventario.setEstado(nuevoInventarioDTO.getEstado());
        inventario.setUbicacion(nuevoInventarioDTO.getUbicacion());

        Inventario guardado = inventarioRepository.save(inventario);

        log.info("Inventario guardado en la base de datos: {}", guardado);
        return convertirADTO(guardado);
    }

    @Transactional
    public InventarioDTO actualizarInventario(Long id, InventarioDTO dto) {
        log.debug("Actualizando inventario en la base de datos: {}", dto);
        return inventarioRepository.findById(id)
                .map(inventario -> {
                    inventario.setTitulo(dto.getTitulo());
                    inventario.setEstado(dto.getEstado());
                    inventario.setUbicacion(dto.getUbicacion());
                    log.info("Inventario actualizado en la base de datos: {}", id);
                    return convertirADTO(inventarioRepository.save(inventario));
                })
                .orElseThrow(() -> new RuntimeException("Inventario con ID " + id + " no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<InventarioDTO> listarInventarios() {

        log.debug("Listando Inventarios en la base de datos");
        return inventarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InventarioDTO buscarPorId(Long id) {

        log.debug("Buscando inventario en la base de datos: {}", id);
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el inventario con ID: " + id));

        log.info("inventario encontrado en la base de datos: {}", inventario);
        return convertirADTO(inventario);
    }

    public void eliminarInventario(Long id) {

        log.debug("Eliminando inventario en la base de datos: {}", id);

        if (!inventarioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: El inventario no existe");
        }

        log.info("Inventario eliminado en la base de datos: {}", id);
        inventarioRepository.deleteById(id);
    }
}
