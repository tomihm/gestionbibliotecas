package dev.diemigo.inventario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class InventarioApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> new InventarioApplication());
	}

}
