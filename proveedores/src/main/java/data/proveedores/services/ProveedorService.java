package data.proveedores.services;
import data.proveedores.model.DTO.proveedorDTO;
import data.proveedores.model.proveedor;
import data.proveedores.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;
    public List<proveedor> findAll()
    {
        return proveedorRepository.findAll();
    }

    public proveedorDTO getproveedor(int Id){
        proveedor proveedor = proveedorRepository.findById(Id).orElseThrow();
        proveedorDTO proveedorDTO = new proveedorDTO();
        proveedorDTO.setEmail_proveedor(proveedor.getEmail_proveedor());
        proveedorDTO.setNombre_proveedor(proveedor.getNombre_proveedor());
        proveedorDTO.setTelefono_proveedor(proveedor.getTelefono_proveedor());
        proveedor guardado = proveedorRepository.save(proveedor);

        return proveedorDTO ;
    }

    public proveedor updateproveedor(proveedor proveedor){
        return proveedorRepository.save(proveedor);
    }

    public void deleteproveedor(int Id){
        proveedorRepository.deleteById(Id);
    }

    public proveedor postproveedor(proveedor proveedor){
        return proveedorRepository.save(proveedor);
    }
}

