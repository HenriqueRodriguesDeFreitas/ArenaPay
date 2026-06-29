package com.arenapay.arenapay.exception;

import com.arenapay.arenapay.dto.response.ErrorResponseDto;
import com.arenapay.arenapay.exception.custom.EntityExistingException;
import com.arenapay.arenapay.exception.custom.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            "ENTITY NOT FOUND", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(EntityExistingException.class)
    public ResponseEntity<ErrorResponseDto> entityExistingException(EntityExistingException e) {
        log.warn("Resource found exception triggered: {}", e.getMessage());
        ErrorResponseDto response = toResponse(HttpStatus.CONFLICT,
            "ENTITY EXISTING", e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    private static ErrorResponseDto toResponse(HttpStatus status,
                                               String erro,
                                               Exception e) {
        return new ErrorResponseDto(LocalDateTime.now().toString(),
            status.value(),
            erro,
            e.getMessage());
    }
}
