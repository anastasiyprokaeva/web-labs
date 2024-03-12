package com.example.WebLab1.Controller;

import com.example.WebLab1.Exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity NotFound(NotFoundException e) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
