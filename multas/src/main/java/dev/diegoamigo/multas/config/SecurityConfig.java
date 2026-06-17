package dev.diegoamigo.multas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitamos CSRF para facilitar las pruebas en entornos distribuidos / REST APIs
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permitir documentación sin autenticación
                        .requestMatchers("/doc/swagger-ui.html", "/doc/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Endpoints de multas expuestos de forma segura (puedes añadir roles específicos si lo requieres)
                        .requestMatchers(HttpMethod.GET, "/api/v1/multas/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/multas/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/multas/**").authenticated()

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}