package com.example.SpringBootDemo.Service;

import com.example.SpringBootDemo.Employee;
import com.example.SpringBootDemo.Repository.EmployeeRepository;
import com.example.SpringBootDemo.Repository.EmployeeRepositoryDBImp;
import com.example.SpringBootDemo.Repository.EmployeeRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Map<String, Long> create(Employee employee) throws EmployeeNotCreatedWithInvalidArgumentsException {

        if (employee.getAge() < 18 || employee.getAge() > 65) {
            throw new EmployeeNotCreatedWithInvalidArgumentsException("Age must be over 18 and below 65.");
        }
        if ((employee.getAge() >= 30 && employee.getSalary() < 20000)) {
            throw new EmployeeNotCreatedWithInvalidArgumentsException("When age is over 30, salary must be larger than 20000.");
        }
        return employeeRepository.save(employee);
    }

    public void clearEmployees() {
        this.employeeRepository.clearEmployees();

    }

    public List<Employee> getEmployees(String gender, Integer page, Integer size) {
        return employeeRepository.getEmployees(gender, page, size);
    }

    public Employee getEmployeeById(long id) throws EmployeeNotFoundException {
        Employee targetEmployee = employeeRepository.getEmployeeById(id);
        if (targetEmployee == null) {
            throw new EmployeeNotFoundException("Employee not found.");
        }
        return targetEmployee;
    }

    public Employee updateEmployee(long id, Employee updateInformation) throws EmployeeAlreadyDeletedException, EmployeeNotFoundException {
        updateInformation.setStatus(true);
        Employee updateEmployee = employeeRepository.updateEmployee(id, updateInformation);
        if (updateEmployee == null) {
            throw new EmployeeNotFoundException("Employee not found.");
        }
        if (!updateEmployee.getStatus()) {
            throw new EmployeeAlreadyDeletedException("Employee already deleted.");
        }

        return updateEmployee;
    }

    public boolean delete(long id) throws EmployeeAlreadyDeletedException, EmployeeNotFoundException {

        Employee removeEmployee = getEmployeeById(id);
        if (!removeEmployee.getStatus()) {
            throw new EmployeeAlreadyDeletedException("Employee already deleted.");
        }
        employeeRepository.delete(id);
        return true;
    }
}
