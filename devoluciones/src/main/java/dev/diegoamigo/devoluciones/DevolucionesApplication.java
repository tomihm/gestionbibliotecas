package dev.diegoamigo.devoluciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // Habilita el funcionamiento de AuditoriaClient
public class DevolucionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevolucionesApplication.class, args);
	}
}