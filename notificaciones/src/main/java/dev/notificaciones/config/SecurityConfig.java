package dev.notificaciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles(Rol.ADMIN.name())
                .build();

        UserDetails bibliotecario = User.builder()
                .username("bibliotecario")
                .password(passwordEncoder().encode("biblio123"))
                .roles(Rol.BIBLIOTECARIO.name())
                .build();

        UserDetails usuario = User.builder()
                .username("usuario")
                .password(passwordEncoder().encode("usuario123"))
                .roles(Rol.USUARIO.name())
                .build();

        return new InMemoryUserDetailsManager(admin, bibliotecario, usuario);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/actuator/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/notificaciones/**")
                        .hasRole(Rol.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/notificaciones/**")
                        .hasAnyRole(Rol.ADMIN.name(), Rol.BIBLIOTECARIO.name())
                        .requestMatchers(HttpMethod.PUT, "/api/notificaciones/**")
                        .hasAnyRole(Rol.ADMIN.name(), Rol.BIBLIOTECARIO.name())
                        .requestMatchers(HttpMethod.GET, "/api/notificaciones/**")
                        .hasAnyRole(Rol.ADMIN.name(), Rol.BIBLIOTECARIO.name(), Rol.USUARIO.name())
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
