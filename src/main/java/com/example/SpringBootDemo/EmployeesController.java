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
    public ResponseEntity<Employee> getEmployee(@PathVariable long id){
        return employeeList.stream()
                .filter(employee -> employee.getId()==id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/employees")
    public List<Employee> getEmployeeByGender(@RequestParam(required = false) String gender){
        if(gender!=null){
            return employeeList.stream().filter(employee -> employee.getGender().equalsIgnoreCase(gender)).collect(Collectors.toList());
        }
        return new ArrayList<>(employeeList);
    }

    @PutMapping("/employees/{id}/age-salary")
    public Employee updateEmployeeAgeAndSalary(@PathVariable  long id,@RequestBody Map<String,Object> updateAgeAndSalary){
        Employee updateEmployee=employeeList.stream().filter(employee -> employee.getId()==id).findFirst().orElse(null);
        if(updateEmployee==null){
            throw new IllegalArgumentException("Invalid employee id");
        }
        if(updateAgeAndSalary.containsKey("age")){
            updateEmployee.setAge((Integer) updateAgeAndSalary.get("age"));
        }
        if(updateAgeAndSalary.containsKey("salary")){
            updateEmployee.setSalary(Double.parseDouble(updateAgeAndSalary.get("salary").toString()));
        }
        return updateEmployee;
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id){
        boolean removed=employeeList.removeIf(employee -> employee.getId()==id);
        if(removed){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
