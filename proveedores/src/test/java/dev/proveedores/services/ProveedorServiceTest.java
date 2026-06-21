package dev.proveedores.services;

import dev.proveedores.model.DTO.proveedorDTO;
import dev.proveedores.model.proveedor;
import dev.proveedores.repository.ProveedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    private proveedor proveedor;

    @BeforeEach
    void setUp() {
        proveedor = new proveedor();
        proveedor.setId_proveedor(1);
        proveedor.setNombre_proveedor("Editorial Sur");
        proveedor.setDireccion_proveedor("Av. Central 123");
        proveedor.setTelefono_proveedor("999999999");
        proveedor.setEmail_proveedor("contacto@editorialsur.cl");
        proveedor.setDescripcion("Proveedor de libros");
    }

    @Test
    void findAllRetornaProveedores() {
        when(proveedorRepository.findAll()).thenReturn(List.of(proveedor));

        List<proveedor> resultado = proveedorService.findAll();

        assertEquals(1, resultado.size());
        assertEquals("Editorial Sur", resultado.getFirst().getNombre_proveedor());
    }

    @Test
    void getProveedorRetornaDto() {
        when(proveedorRepository.findById(1)).thenReturn(Optional.of(proveedor));
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);

        proveedorDTO resultado = proveedorService.getproveedor(1);

        assertEquals("Editorial Sur", resultado.getNombre_proveedor());
        assertEquals("999999999", resultado.getTelefono_proveedor());
        assertEquals("contacto@editorialsur.cl", resultado.getEmail_proveedor());
    }

    @Test
    void updateProveedorGuardaEntidad() {
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);

        proveedor resultado = proveedorService.updateproveedor(proveedor);

        assertEquals("Editorial Sur", resultado.getNombre_proveedor());
        verify(proveedorRepository).save(proveedor);
    }

    @Test
    void postProveedorGuardaEntidad() {
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);

        proveedor resultado = proveedorService.postproveedor(proveedor);

        assertEquals(1, resultado.getId_proveedor());
        verify(proveedorRepository).save(proveedor);
    }

    @Test
    void deleteProveedorEliminaPorId() {
        proveedorService.deleteproveedor(1);

        verify(proveedorRepository).deleteById(1);
    }
}
