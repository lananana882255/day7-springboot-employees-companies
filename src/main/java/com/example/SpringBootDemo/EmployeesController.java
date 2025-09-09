package com.example.SpringBootDemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EmployeesController {
    private List<Employee> employeeList= new ArrayList<>();

    @PostMapping("/employees")
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody Employee employee){
        employeeList.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",employee.getId()));
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable long id){
        return employeeList.stream().filter(employee -> employee.getId()==id).findFirst().orElse(null);
    }

    @GetMapping("/employees")
    public List<Employee> getEmployeeByGender(@RequestParam(required = false) String gender){
        if(gender!=null){
            return employeeList.stream().filter(employee -> employee.getGender().equalsIgnoreCase(gender)).collect(Collectors.toList());
        }
        return new ArrayList<>(employeeList);
    }

    @PutMapping("/employees")
    public Employee updateEmployeeAgeAndSalary(@RequestParam Long id,int age,double salary){
        Employee updateEmployee=employeeList.stream().filter(employee -> employee.getId()==id).findFirst().orElse(null);
        updateEmployee.setAge(age);
        updateEmployee.setSalary(salary);
        return updateEmployee;
    }

}
