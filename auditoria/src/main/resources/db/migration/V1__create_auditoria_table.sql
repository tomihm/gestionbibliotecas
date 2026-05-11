CREATE TABLE IF NOT EXISTS registros_auditoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    servicio_origen VARCHAR(255) NOT NULL,
    accion VARCHAR(255) NOT NULL,
    detalle TEXT,
    usuario_responsable VARCHAR(255),
    fecha_hora DATETIME NOT NULL,
    resultado VARCHAR(255) NOT NULL
    );

CREATE INDEX idx_servicio_origen ON registros_auditoria(servicio_origen);
CREATE INDEX idx_resultado ON registros_auditoria(resultado);
CREATE INDEX idx_fecha_hora ON registros_auditoria(fecha_hora);