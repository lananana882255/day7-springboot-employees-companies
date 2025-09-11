package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Employee;
import com.example.SpringBootDemo.Service.EmployeeAlreadyDeletedException;
import com.example.SpringBootDemo.Service.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class EmployeeRepositoryDBImp implements EmployeeRepository {
    @Autowired
    private EmployeeJPARepository employeeJPARepository;

    @Override
    public Map<String, Long> save(Employee employee) {
        employeeJPARepository.save(employee);
        return Map.of("id", employee.getId());
    }

    @Override
    public void clearEmployees() {
        employeeJPARepository.deleteAll();
    }

    @Override
    public List<Employee> getEmployees(String gender, Integer page, Integer size) {
        return employeeJPARepository.findByGender(gender);
    }

    @Override
    public void delete(long id) throws EmployeeAlreadyDeletedException {
        Employee deletedEmployee = employeeJPARepository.findById(id).get();
        deletedEmployee.setStatus(false);
        employeeJPARepository.save(deletedEmployee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeJPARepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(""));
    }

    @Override
    public Employee updateEmployee(long id, Employee updateEmployee) {
        updateEmployee.setId(id);
        return employeeJPARepository.save( updateEmployee);
    }

}
