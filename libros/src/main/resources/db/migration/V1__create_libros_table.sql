CREATE TABLE libros(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(20),
    autor VARCHAR(35) NOT NULL,
    categoria VARCHAR(15),
    isbn VARCHAR(17)
);