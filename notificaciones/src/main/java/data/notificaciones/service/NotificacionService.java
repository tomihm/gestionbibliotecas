package data.notificaciones.service;

import data.notificaciones.model.Notificacion;
import data.notificaciones.repository.NotificacionRepository;
import data.notificaciones.model.DTO.DTONotificaciones;
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
    public DTONotificaciones actualizarNotificacion(DTONotificaciones notificacionDTO) {
        Notificacion notificacion = repositoryNotificacion.findById(notificacionDTO);{
            if (notificacion == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"no se ha encontrado el archivo");

            }
        }
        return convertirDTO(notificacion);
    }


    public void deleteNotificaciones(int id){repositoryNotificacion.deleteById(id);
    }

    
}
