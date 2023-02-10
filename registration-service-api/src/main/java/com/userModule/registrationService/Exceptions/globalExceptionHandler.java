package com.userModule.registrationService.Exceptions;

import com.userModule.registrationService.Payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Author : Rohit Parihar
 * Date : 11/24/2022
 * Time : 4:30 PM
 * Class : globalExceptionHandler
 * Project : Bloggios-Backend
 */

@RestControllerAdvice
public class globalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> apiException(ApiException apiException){
        String message = apiException.getMessage();
        HttpStatus status = apiException.getStatus();
        return new ResponseEntity<>(new ApiResponse(message), status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNoValidException(MethodArgumentNotValidException exception){
        Map<String, String> response = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((e)-> {
            String field = ((FieldError) e).getField();
            String defaultMessage = e.getDefaultMessage();
            response.put(field, defaultMessage);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
