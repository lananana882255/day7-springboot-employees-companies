package com.example.SpringBootDemo.Service;

public class EmployeeNotCreatedWithInvalidArgumentsException extends RuntimeException {
    private String message;

    public EmployeeNotCreatedWithInvalidArgumentsException(String message) {
        super(message);
    }

}
