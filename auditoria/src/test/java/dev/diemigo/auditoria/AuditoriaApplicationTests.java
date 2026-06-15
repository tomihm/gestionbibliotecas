package dev.diemigo.auditoria;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(
		properties = {
				"eureka.client.enabled=false",
				"spring.cloud.discovery.enabled=false"
		}
)
class AuditoriaApplicationTests {

	@Test
	void contextLoads() {
	}
}