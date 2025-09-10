package com.example.SpringBootDemo.Service;

public class EmployeeAlreadyDeletedException extends Exception {
    public EmployeeAlreadyDeletedException(String message) {
        super(message);
    }
}
