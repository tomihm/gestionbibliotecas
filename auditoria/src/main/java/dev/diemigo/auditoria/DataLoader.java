package dev.diemigo.auditoria;

import dev.diemigo.dev.auditoria.model.RegistroAuditoria;
import dev.diemigo.dev.auditoria.repository.RegistroAuditoriaRepository;
import net.datafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private final RegistroAuditoriaRepository auditoriaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (auditoriaRepository.count() > 0) {
            log.info("La base de datos de auditoría ya contiene registros. Saltando el DataLoader.");
            return;
        }

        log.info("Iniciando la carga automática de datos de prueba (Profile: dev)...");
        Faker faker = new Faker();
        Random random = new Random();

        List<String> servicios = List.of("SERVICIO-LIBROS", "SERVICIO-USUARIOS", "SERVICIO-PRESTAMOS", "API-GATEWAY");
        List<String> acciones = List.of("CREAR_LIBRO", "ELIMINAR_USUARIO", "SOLICITAR_PRESTAMO", "ACTUALIZAR_STOCK", "LOGIN_FALLIDO");
        List<String> resultados = List.of("EXITOSO", "EXITOSO", "EXITOSO", "FALLIDO", "ERROR_INTERNO");

        for (int i = 0; i < 50; i++) {
            RegistroAuditoria registro = new RegistroAuditoria();
            registro.setServicioOrigen(servicios.get(random.nextInt(servicios.size())));
            registro.setAccion(acciones.get(random.nextInt(acciones.size())));
            registro.setResultado(resultados.get(random.nextInt(resultados.size())));
            registro.setUsuarioResponsable(faker.internet().username());
            registro.setDetalle("Operación simulada automáticamente en entorno DEV. UUID del Payload: " + faker.internet().uuid());

            Date fechaPasada = faker.date().past(30, TimeUnit.DAYS);
            LocalDateTime fechaHora = fechaPasada.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            registro.setFechaHora(fechaHora);
            auditoriaRepository.save(registro);
        }

        log.info("¡Carga masiva finalizada exitosamente! Se han insertado {} registros ficticios.", auditoriaRepository.count());
    }
}