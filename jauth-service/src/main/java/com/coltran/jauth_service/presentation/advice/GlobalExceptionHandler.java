package com.coltran.jauth_service.presentation.advice;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.coltran.jauth_service.application.dto.ErrorResponse;
import com.coltran.jauth_service.domain.exception.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserException(
        UserNotFoundException ex, 
        HttpServletRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI(),
            OffsetDateTime.now(ZoneOffset.UTC)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        
    }
    
}
