package com.example.SpringBootDemo.Service;

public class EmployeeNotCreatedWithInvalidArgumentsException extends Exception{
    private String message;
    public EmployeeNotCreatedWithInvalidArgumentsException(String message) {
        super(message);
    }

}
