package data.proveedores.services;

import data.proveedores.model.proveedor;
import data.proveedores.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;
    public List<proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    public proveedor getproveedor(int Id){return proveedorRepository.findById(Id).get();}

    public proveedor updateproveedor(proveedor proveedor){return proveedorRepository.save(proveedor);}

    public void deleteproveedor(int Id){proveedorRepository.deleteById(Id);}

}

