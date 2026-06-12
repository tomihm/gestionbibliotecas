package data.notificaciones.repository;
import data.notificaciones.model.DTO.DTONotificaciones;
import data.notificaciones.model.Notificacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    Notificacion findById(DTONotificaciones notificacionDTO);
}


