package com.example.SpringBootDemo.Service;

public class EmployeeAlreadyExistedException extends RuntimeException{

    public EmployeeAlreadyExistedException(String message) {
        super(message);
    }
}
