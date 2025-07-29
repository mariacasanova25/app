package com.example.app.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> handleNumberFormatException(NumberFormatException exception) {
        var status = HttpStatus.BAD_REQUEST;
        var body = buildResponse(status, "Invalid user id format. Must be a number.");
        return new ResponseEntity<>(body, status);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var body = buildResponse(status, "An unexpected error occurred.");
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException exception) {
        var status = HttpStatus.NOT_FOUND;
        var body = buildResponse(status, exception.getMessage());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException exception) {
        var status = HttpStatus.BAD_REQUEST;
        var body = buildResponse(status, exception.getMessage());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        var status = HttpStatus.BAD_REQUEST;
        var body = buildResponse(status, exception.getMessage());
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        if (body != null) {
            body.put("errors", errors);
        }
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception exception) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> body = buildResponse(status, "An unexpected error occurred.");
        return new ResponseEntity<>(body, status);
    }

    private Map<String, Object> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return body;
    }
}
