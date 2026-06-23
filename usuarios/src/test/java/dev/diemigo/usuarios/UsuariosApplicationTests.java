package dev.diemigo.usuarios;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UsuariosApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> new UsuariosApplication());
	}

}
