package data.notificaciones.service;

import data.notificaciones.model.Notificacion;
import data.notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {
    @Autowired
    private NotificacionRepository repositoryNotificacion;

    public List<Notificacion> obtenerNotificaciones() {return repositoryNotificacion.findAll();
    }
    public Notificacion getNotificaciones(int id){return repositoryNotificacion.findById(id).get();
    }

    public Notificacion addNotificacion(Notificacion modelNotificacion){
        repositoryNotificacion.save(modelNotificacion);
        return modelNotificacion;
    }
    public void deleteNotificaciones(int id){repositoryNotificacion.deleteById(id);
    }
    
}
