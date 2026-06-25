package dev.diemigo.libros.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestException extends RuntimeException {

    public RequestException(String message) {
        super(message);
    }
}
