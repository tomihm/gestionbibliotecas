package data.proveedores.controller;
import data.proveedores.model.proveedor;
import data.proveedores.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/proveedor")
public class proveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public List<proveedor> findAll() {
        return proveedorService.findAll();
    }

    @GetMapping("/{id}")
    public proveedor findById(@PathVariable @RequestParam int id) {
        return proveedorService.getproveedor(id);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @RequestParam int id) {
        proveedorService.deleteproveedor(id);
    }
}