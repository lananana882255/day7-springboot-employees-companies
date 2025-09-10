package com.example.SpringBootDemo.Service;

import com.example.SpringBootDemo.Employee;
import com.example.SpringBootDemo.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    private static long nextId=1;
    @Autowired
    private EmployeeRepository employeeRepository;
    public Map<String,Long> create(Employee employee) throws EmployeeNotCreatedWithInvalidArgumentsException {
        if(employee.getAge()<18||employee.getAge()>65||(employee.getAge()>30&&employee.getSalary()<20000)){
            throw new EmployeeNotCreatedWithInvalidArgumentsException("Invalid arguments.");
        }
        employee.setId(nextId++);
        employee.setStatus(true);
        employeeRepository.save(employee);
        return Map.of("id",employee.getId());
    }

    public void clearEmployees() {
        this.employeeRepository.clearEmployees();
        nextId=1;
    }

    public List<Employee> getEmployees(String gender,Integer page,Integer size) {
        return employeeRepository.getEmployees(gender,page,size);
    }

    public Employee getEmployeeById(long id) throws EmployeeNotFoundException {
        Employee targetEmployee=employeeRepository.getEmployeeById(id);
        if(targetEmployee==null){
            throw new EmployeeNotFoundException();
        }
        return targetEmployee;
    }

    public Employee updateEmployee(long id, Map<String, Object> updateInformation) {
        Employee updateEmployee=employeeRepository.updateEmployee(id,updateInformation);

        return updateEmployee;
    }

    public boolean delete(long id) {
        return employeeRepository.delete(id);
    }
}
