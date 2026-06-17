package dev.diemigo.devoluciones.config;

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
                // Deshabilitamos CSRF ya que nos comunicamos vía APIs REST sin estado
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Documentación libre de Swagger
                        .requestMatchers("/doc/swagger-ui.html", "/doc/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Endpoints de negocio protegidos o libres según verbo HTTP
                        .requestMatchers(HttpMethod.GET, "/api/v1/devoluciones/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/devoluciones/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/devoluciones/**").authenticated()

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}