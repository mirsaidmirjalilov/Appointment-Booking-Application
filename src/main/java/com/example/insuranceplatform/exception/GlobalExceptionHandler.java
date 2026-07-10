package com.example.insuranceplatform.exception;

import com.example.insuranceplatform.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> appointmentNotFoundException(AppointmentNotFoundException exception) {
        return ResponseEntity.status(404).body(ErrorResponse.error(exception.getMessage(), 404, "/api/v1/appointments"));
    }

    @ExceptionHandler(AvailabilityNotFoundException.class)
    public ResponseEntity<ErrorResponse> availabilityNotFoundException(AvailabilityNotFoundException exception) {
        return ResponseEntity.status(404).body(ErrorResponse.error(exception.getMessage(), 404, "/api/v1/availability"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.error(exception.getMessage(), 403, "/api/v1"));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> runtimeException(RuntimeException exception) {
        return ResponseEntity.status(500).body(ErrorResponse.error(exception.getMessage(), 500, "/api/v1"));
    }
}
