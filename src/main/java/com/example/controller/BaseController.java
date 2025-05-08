package com.example.controller;

import com.example.model.ErrorForm;
import com.example.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class BaseController {
    private final HttpHeaders headers;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> handle(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.info(message);
        return new ResponseEntity<>(new ErrorForm(message), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    ResponseEntity<?> handle(IllegalArgumentException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(new ErrorForm(e.getMessage()), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    ResponseEntity<?> handle(NotFoundException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(new ErrorForm(e.getMessage()), headers, HttpStatus.NOT_FOUND);
    }
}
