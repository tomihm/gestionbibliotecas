package dev.diegoamigo.multas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // <-- Obligatorio para activar OpenFeign
public class MultasApplication {
	public static void main(String[] args) {
		SpringApplication.run(MultasApplication.class, args);
	}
}