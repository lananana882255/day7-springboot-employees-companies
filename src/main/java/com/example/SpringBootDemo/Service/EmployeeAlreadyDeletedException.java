package com.example.SpringBootDemo.Service;

public class EmployeeAlreadyDeletedException extends RuntimeException {
    public EmployeeAlreadyDeletedException(String message) {
        super(message);
    }
}
