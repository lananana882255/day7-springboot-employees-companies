package com.example.SpringBootDemo.Controller;

import com.example.SpringBootDemo.Employee;
import com.example.SpringBootDemo.Service.EmployeeAlreadyDeletedException;
import com.example.SpringBootDemo.Service.EmployeeNotCreatedWithInvalidArgumentsException;
import com.example.SpringBootDemo.Service.EmployeeNotFoundException;
import com.example.SpringBootDemo.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/employees")
@RestController
public class EmployeesController {

    @Autowired
    private EmployeeService employeeService;
    public void clearEmployees() {
        this.employeeService.clearEmployees();
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> createEmployee(@RequestBody Employee employee) throws EmployeeNotCreatedWithInvalidArgumentsException {

        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable long id) throws EmployeeNotFoundException {
            return ResponseEntity.ok(employeeService.getEmployeeById(id));
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
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) throws EmployeeNotFoundException, EmployeeAlreadyDeletedException {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
