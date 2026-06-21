package dev.proveedores.controller;

import dev.proveedores.model.DTO.proveedorDTO;
import dev.proveedores.model.proveedor;
import dev.proveedores.services.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProveedorControllerTest {

    @Mock
    private ProveedorService proveedorService;

    @InjectMocks
    private proveedorController controller;

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
    void findAllRetornaLista() {
        when(proveedorService.findAll()).thenReturn(List.of(proveedor));

        List<proveedor> resultado = controller.findAll();

        assertEquals(1, resultado.size());
    }

    @Test
    void findByIdRetornaDto() {
        proveedorDTO dto = new proveedorDTO("Editorial Sur", "999999999", "contacto@editorialsur.cl", "Proveedor de libros");
        when(proveedorService.getproveedor(1)).thenReturn(dto);

        proveedorDTO resultado = controller.findById(1);

        assertEquals("Editorial Sur", resultado.getNombre_proveedor());
    }

    @Test
    void updateProveedorRetornaProveedor() {
        when(proveedorService.updateproveedor(proveedor)).thenReturn(proveedor);

        proveedor resultado = controller.updateProveedor(proveedor);

        assertEquals(1, resultado.getId_proveedor());
    }

    @Test
    void addProveedorRetornaProveedor() {
        when(proveedorService.postproveedor(proveedor)).thenReturn(proveedor);

        proveedor resultado = controller.addProveedor(proveedor);

        assertEquals("Editorial Sur", resultado.getNombre_proveedor());
    }

    @Test
    void deleteProveedorInvocaServicio() {
        controller.deleteProveedor(1);

        verify(proveedorService).deleteproveedor(1);
    }
}
