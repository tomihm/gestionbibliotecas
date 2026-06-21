INSERT INTO reservas (libro_id, usuario_id, fecha_reserva, fecha_limite_retiro, disponible_actualmente, estado, observacion)
VALUES
    (101, 2001, NOW(), DATE_ADD(NOW(), INTERVAL 3 DAY), b'0', 'PENDIENTE', 'Reserva de ejemplo para libro sin stock');
