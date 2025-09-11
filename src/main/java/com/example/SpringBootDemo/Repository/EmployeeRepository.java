package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Employee;
import com.example.SpringBootDemo.Service.EmployeeAlreadyDeletedException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeRepository  {
    Map<String, Long> save(Employee employee);

    void clearEmployees();

    List<Employee> getEmployees(String gender, Integer page, Integer size);

    void delete(long id) throws EmployeeAlreadyDeletedException;

    Employee getEmployeeById(long id);

    Employee updateEmployee(long id, Employee updateEmployee);
}
