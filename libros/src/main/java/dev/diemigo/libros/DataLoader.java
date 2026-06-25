package dev.diemigo.libros;

import dev.diemigo.libros.model.Libro;
import dev.diemigo.libros.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Autowired
    private LibroRepository libroRepository;

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Override
    public void run(String... args) throws Exception {

        if (libroRepository.count() > 0) {
            log.info("La base de datos de libros ya contiene registros. Saltando el DataLoader.");
            return;
        }

        log.info("Cargando datos de prueba (Profile: dev)...");
        Faker faker = new Faker();
        Random random = new Random();

        // Generar libros
        for (long i = 0; i < 50; i++) {
            Libro libro = new Libro();
            libro.setId(i + 1);
            libro.setTitulo(faker.book().title());
            libro.setAutor(faker.book().author());
            libro.setCategoria(faker.book().genre());
            libro.setIsbn(faker.code().isbn10());
            libroRepository.save(libro);
        }

        List<Libro> libros = libroRepository.findAll();
        log.info("¡Carga masiva finalizada exitosamente! Se han insertado {} libros ficticios.", libroRepository.count());

    }
}