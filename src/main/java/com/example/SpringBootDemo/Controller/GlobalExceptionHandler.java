package com.example.SpringBootDemo.Controller;

import com.example.SpringBootDemo.Service.EmployeeAlreadyDeletedException;
import com.example.SpringBootDemo.Service.EmployeeNotCreatedWithInvalidArgumentsException;
import com.example.SpringBootDemo.Service.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotCreatedWithInvalidArgumentsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmployeeNotCreatedWithInvalidArgumentsException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmployeeNotFoundException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmployeeAlreadyDeletedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmployeeAlreadyDeletedException(Exception e) {
        return e.getMessage();
    }

}
