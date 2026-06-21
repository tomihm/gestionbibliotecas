CREATE TABLE IF NOT EXISTS reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    libro_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    fecha_reserva DATETIME NOT NULL,
    fecha_limite_retiro DATETIME NOT NULL,
    disponible_actualmente BIT(1) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    observacion VARCHAR(255),
    CONSTRAINT uq_reserva_libro_usuario UNIQUE (libro_id, usuario_id, estado)
);

CREATE INDEX idx_reservas_libro ON reservas(libro_id);
CREATE INDEX idx_reservas_usuario ON reservas(usuario_id);
CREATE INDEX idx_reservas_estado ON reservas(estado);
