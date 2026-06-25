CREATE TABLE libros(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255),
    autor VARCHAR(255) NOT NULL,
    categoria VARCHAR(100),
    isbn VARCHAR(17)
);
