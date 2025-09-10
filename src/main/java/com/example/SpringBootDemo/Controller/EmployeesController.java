package com.example.SpringBootDemo.Controller;

import com.example.SpringBootDemo.Employee;
import com.example.SpringBootDemo.EmployeeNotCreatedWithInvalidAgeException;
import com.example.SpringBootDemo.EmployeeNotFoundException;
import com.example.SpringBootDemo.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/employees")
@RestController
public class EmployeesController {

    @Autowired
    private EmployeeService employeeService;
    public void clearEmployees() {
        this.employeeService.clearEmployees();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody Employee employee) {
        try {
            employeeService.create(employee);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",employee.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable long id) throws EmployeeNotFoundException {
        try {
            Employee result = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public List<Employee> getEmployees(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size){
        return employeeService.getEmployees(gender,page,size);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeAgeAndSalary(@PathVariable  long id,@RequestBody Map<String,Object> updateInformation) {
        Employee updateEmployee=employeeService.updateEmployee(id,updateInformation);
        if(updateEmployee==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id){
        if(employeeService.delete(id)){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
