package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Employee;
import com.example.SpringBootDemo.Service.EmployeeAlreadyDeletedException;
import com.example.SpringBootDemo.Service.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class EmployeeRepositoryDBImp implements EmployeeRepository {
    @Autowired
    private EmployeeJPARepository employeeJPARepository;

    @Override
    public Map<String, Long> save(Employee employee) {
        employee.setStatus(true);
        Employee savedEmployee =employeeJPARepository.save(employee);
        return Map.of("id", savedEmployee.getId());
    }

    @Override
    public void clearEmployees() {
        employeeJPARepository.deleteAll();
    }

    @Override
    public List<Employee> getEmployees(String gender, Integer page, Integer size) {
        if (gender != null) {
            return employeeJPARepository.findByGender(gender);
        }

        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page - 1, size);
            return employeeJPARepository.findAll(pageable).getContent();
        }
        return employeeJPARepository.findAll();
    }

    @Override
    public void delete(long id) throws EmployeeAlreadyDeletedException {
        Employee deletedEmployee = employeeJPARepository.findById(id)
                .orElse(null);
        deletedEmployee.setStatus(false);
        employeeJPARepository.save(deletedEmployee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeJPARepository.findById(id).orElse(null);
    }

    @Override
    public Employee updateEmployee(long id, Employee updateEmployee) {
        updateEmployee.setId(id);
        Employee existingEmployee = employeeJPARepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found"));
        if (updateEmployee.getAge() != 0) {
            existingEmployee.setAge(updateEmployee.getAge());
        }
        if (updateEmployee.getSalary() != 0) {
            existingEmployee.setSalary(updateEmployee.getSalary());
        }
        if (updateEmployee.getName() != null) {
            existingEmployee.setName(updateEmployee.getName());
        }
        if (updateEmployee.getGender() != null) {
            existingEmployee.setGender(updateEmployee.getGender());
        }
        return employeeJPARepository.save( updateEmployee);
    }

}
