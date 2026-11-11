package com.example.demoCrud.exception;

import com.example.demoCrud.controllers.AutorRestController;
import com.example.demoCrud.controllers.LibroRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// Convierte las ApiException en respuestas JSON solo para los controladores REST
@RestControllerAdvice(assignableTypes = {LibroRestController.class, AutorRestController.class})
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, Object>> handle(ApiException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(Map.of("status", ex.getStatus().value(), "error", ex.getMessage()));
    }
}
