package org.example.enumtalentapi.exception;

import org.example.enumtalentapi.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleCustom(CustomException ex){
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
