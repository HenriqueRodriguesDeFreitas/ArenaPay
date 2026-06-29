package com.arenapay.arenapay.exception;

import com.arenapay.arenapay.dto.response.ErrorResponseDto;
import com.arenapay.arenapay.exception.custom.EntityExistingException;
import com.arenapay.arenapay.exception.custom.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> entityNotFoundException(NotFoundException e) {
        log.warn("Resource not found exception triggered: {}", e.getMessage());

        ErrorResponseDto response = toResponse(HttpStatus.NOT_FOUND,
            "ENTITY NOT FOUND", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(EntityExistingException.class)
    public ResponseEntity<ErrorResponseDto> entityExistingException(EntityExistingException e) {
        log.warn("Resource found exception triggered: {}", e.getMessage());
        ErrorResponseDto response = toResponse(HttpStatus.CONFLICT,
            "ENTITY EXISTING", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> constraintViolationException(ConstraintViolationException e) {
        log.warn("Constraint violation detected: {}", e.getMessage());
        ErrorResponseDto response = toResponse(HttpStatus.BAD_REQUEST, "CONSTRAINT VIOLATION", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("Validation failed for argument: {}", e.getMessage());

        // Pega a primeira mensagem de erro de validação encontrada para exibir de forma limpa
        String fieldMessage = e.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .findFirst()
            .orElse("Validation failed");

        ErrorResponseDto response = toResponse(HttpStatus.BAD_REQUEST, "VALIDATION FAILED", fieldMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private static ErrorResponseDto toResponse(HttpStatus status,
                                               String erro,
                                               String message) {
        return new ErrorResponseDto(LocalDateTime.now().toString(),
            status.value(),
            erro,
            message);
    }
}
