package com.coltran.jauth_service.presentation.advice;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.coltran.jauth_service.application.dto.ErrorResponse;
import com.coltran.jauth_service.domain.exception.UserException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<ErrorResponse> handleUserException(
        UserException ex, 
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
