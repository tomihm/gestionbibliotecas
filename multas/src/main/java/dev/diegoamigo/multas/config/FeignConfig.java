package dev.diegoamigo.multas.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    /**
     * Interceptor global de Feign para inyectar automáticamente las credenciales HTTP Basic
     * cuando llamemos al microservicio de Auditoría.
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        // Usamos las credenciales del "bibliotecario" configurado en tu microservicio de auditoría
        return new BasicAuthRequestInterceptor("bibliotecario", "biblio123");
    }
}