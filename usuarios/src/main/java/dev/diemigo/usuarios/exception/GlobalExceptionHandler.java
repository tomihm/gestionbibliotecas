    package dev.diemigo.usuarios.exception;

    import dev.diemigo.usuarios.dto.ExceptionDTO;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ExceptionDTO> handleGeneralException(Exception e) {
            ExceptionDTO error = new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR, e);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ExceptionDTO> handleNotFound(RuntimeException e) {
            ExceptionDTO error = new ExceptionDTO(HttpStatus.NOT_FOUND, e);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }