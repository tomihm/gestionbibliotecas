package data.notificaciones.controller;

import data.notificaciones.service.NotificacionService;
import data.notificaciones.model.DTO.DTONotificaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {
    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    public List<DTONotificaciones> findAll() {
        return notificacionService.obtenerNotificaciones();

    }

    @GetMapping()
    public DTONotificaciones findById(@PathVariable int id) {

        return notificacionService.getNotificacionesById(id);
    }

    @PostMapping
    public DTONotificaciones createNotificacion(@RequestBody DTONotificaciones notificacion) {
        return notificacionService.addNotificacion(notificacion);
    }

    @PutMapping()
    public DTONotificaciones actualizarNotificacion(@RequestBody DTONotificaciones notificacion) {
        return notificacionService.actualizarNotificacion(notificacion);
    }


    @DeleteMapping()
    public void deleteById(@PathVariable int id)
    {
        notificacionService.deleteNotificaciones(id);
    }

}
