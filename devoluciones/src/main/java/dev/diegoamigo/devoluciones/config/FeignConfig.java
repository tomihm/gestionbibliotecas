package dev.diemigo.devoluciones.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    /**
     * Interceptor global para que OpenFeign inyecte automáticamente la autenticación
     * básica requerida por el microservicio seguro de Auditoría.
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        // Credenciales válidas del rol BIBLIOTECARIO configuradas en tu servicio de auditoría
        return new BasicAuthRequestInterceptor("bibliotecario", "biblio123");
    }
}