package com.github.exceptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.github.domain.ResponseDomain;

@ControllerAdvice
public class ApiExceptionHandlers {

    @ExceptionHandler({ IOException.class, ClassCastException.class, RuntimeException.class })
    public ResponseEntity<Object> ioError(IOException e) {
        Map<String, String> message = new HashMap<>();
        message.put(e.getClass().getSimpleName(), e.getMessage());

        return ResponseEntity.internalServerError()
                .body(new ResponseDomain.Builder()
                        .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .setStatusCode(500)
                        .setMessage(message)
                        .build());
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Object> validationError(MethodArgumentNotValidException e) {
        Map<String, String> message = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.joining(" and "))));

        return ResponseEntity.badRequest().body(new ResponseDomain.Builder()
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setStatusCode(500)
                .setMessage(message)
                .build());
    }
}
