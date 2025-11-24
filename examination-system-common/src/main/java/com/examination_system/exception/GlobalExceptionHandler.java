package com.examination_system.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.ws.rs.core.Response;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> hadleRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(Response.Status.BAD_REQUEST.getStatusCode()).body(exception.getMessage());
    }
}
