package dev.diemigo.libros;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LibrosApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> new LibrosApplication());
	}

}
