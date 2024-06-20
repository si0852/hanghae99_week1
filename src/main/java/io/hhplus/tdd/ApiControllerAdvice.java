package io.hhplus.tdd;

import io.hhplus.tdd.exception.BadRequestPointException;
import io.hhplus.tdd.exception.NotEnoughPointException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse("500", "error : " +e.getMessage()));
    }

    @ExceptionHandler(value = NotEnoughPointException.class)
    public ResponseEntity<ErrorResponse> NotEnoughPointException(NotEnoughPointException e) {
        return ResponseEntity.status(500).body(e.getErrorResponse());
    }

    @ExceptionHandler(value = BadRequestPointException.class)
    public ResponseEntity<ErrorResponse> BadRequestPointException(BadRequestPointException e) {
        return ResponseEntity.status(500).body(e.getErrorResponse());
    }
}
