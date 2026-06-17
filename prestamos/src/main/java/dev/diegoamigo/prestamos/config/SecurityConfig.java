package dev.diemigo.prestamos.config;

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
                // Deshabilitamos CSRF al ser una API REST sin estado
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Documentación de Swagger libre
                        .requestMatchers("/doc/swagger-ui.html", "/doc/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Endpoints protegidos según verbo HTTP
                        .requestMatchers(HttpMethod.GET, "/api/v1/prestamos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/prestamos/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/prestamos/**").authenticated()

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}