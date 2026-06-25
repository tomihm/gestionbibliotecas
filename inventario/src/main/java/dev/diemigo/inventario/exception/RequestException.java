package dev.diemigo.inventario.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestException extends RuntimeException {

    public RequestException(String message) {
        super(message);
    }
}
