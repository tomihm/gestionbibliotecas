package dev.notificaciones.service;

import dev.notificaciones.model.Notificacion;
import dev.notificaciones.repository.NotificacionRepository;
import dev.notificaciones.model.DTO.DTONotificaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;
import java.util.List;

@Service
public class NotificacionService {
    @Autowired
    private NotificacionRepository repositoryNotificacion;

    private DTONotificaciones convertirDTO(Notificacion notificacion) {
     return new DTONotificaciones(notificacion.getDia_entrega(),notificacion.getNotificacion_titulo(),notificacion.getDescripcion());
    }

    public List<DTONotificaciones> obtenerNotificaciones() {
        return repositoryNotificacion.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }


    public DTONotificaciones getNotificacionesById (int id){
        Notificacion notificacion = repositoryNotificacion.findById(id).orElseThrow();
        return convertirDTO(notificacion);
    }


    public DTONotificaciones addNotificacion(DTONotificaciones nuevoNotificacionDTO){
        Notificacion notificacion = new Notificacion();
        notificacion.setDia_entrega(nuevoNotificacionDTO.getDia_entrega());
        notificacion.setNotificacion_titulo(nuevoNotificacionDTO.getNotificacion_titulo());
        notificacion.setDescripcion(nuevoNotificacionDTO.getDescripcion());

        Notificacion guardado = repositoryNotificacion.save(notificacion);
        return convertirDTO(guardado);
    }
    public DTONotificaciones actualizarNotificacion(int id, DTONotificaciones notificacionDTO) {
        Notificacion notificacion = repositoryNotificacion.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la notificacion"));

        notificacion.setDia_entrega(notificacionDTO.getDia_entrega());
        notificacion.setNotificacion_titulo(notificacionDTO.getNotificacion_titulo());
        notificacion.setDescripcion(notificacionDTO.getDescripcion());

        Notificacion actualizado = repositoryNotificacion.save(notificacion);
        return convertirDTO(actualizado);
    }


    public void deleteNotificaciones(int id){repositoryNotificacion.deleteById(id);
    }

    
}
