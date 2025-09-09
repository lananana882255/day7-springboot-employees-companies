package com.example.SpringBootDemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeesController {
    private List<Employee> employeeList= new ArrayList<>();

    @PostMapping("/employees")
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody Employee employee){
        employeeList.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",employee.getId()));
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable long employeeId){
        return employeeList.stream().filter(employee -> employee.getId()==employeeId).findFirst().orElse(null);
    }

    @GetMapping("employees")
    public List<Employee> getEmployeeByGender(@RequestParam String gender){
        return employeeList.stream().filter(employee -> employee.getGender()==gender).toList();
    }



}
