package data.notificaciones.controller;

import data.notificaciones.model.Notificacion;
import data.notificaciones.service.NotificacionService;
import data.notificaciones.model.DTO.DTONotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class notificacionController {
    @Autowired
    private NotificacionService NotificacionService;

    @GetMapping
    public List<Notificacion> findAll() {
        return NotificacionService.obtenerNotificaciones();

    }

    @GetMapping("/{id}")
    public Notificacion findById(@PathVariable int id) {

        return NotificacionService.getNotificaciones(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id)
    {
        NotificacionService.deleteNotificaciones(id);
    }

}
