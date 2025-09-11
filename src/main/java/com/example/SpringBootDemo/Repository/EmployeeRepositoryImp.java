package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Employee;
import com.example.SpringBootDemo.Service.EmployeeAlreadyDeletedException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeRepositoryImp implements  EmployeeRepository {
    private final List<Employee> employeeList = new ArrayList<>();
    private static long nextId = 1;

    @Override
    public Map<String, Long> save(Employee employee) {
        employee.setId(nextId++);
        employee.setStatus(true);
        employeeList.add(employee);
        return Map.of("id",employee.getId());
    }

    @Override
    public void clearEmployees() {
        this.employeeList.clear();
        nextId = 1;
    }

    @Override
    public List<Employee> getEmployees(String gender, Integer page, Integer size) {
        List<Employee> filteredEmployeesList = employeeList;
        if (gender != null) {
            filteredEmployeesList = filteredEmployeesList.stream().filter(employee -> employee.getGender().equalsIgnoreCase(gender)).collect(Collectors.toList());
        }
        if (page != null && size != null) {
            int start = (page - 1) * size;
            if (start >= filteredEmployeesList.size()) {
                return Collections.emptyList();
            }
            int end = Math.min(start + size, filteredEmployeesList.size());
            filteredEmployeesList = filteredEmployeesList.subList(start, end);
        }
        return filteredEmployeesList;
    }

    @Override
    public void delete(long id) throws EmployeeAlreadyDeletedException {
        Employee removeEmployee = getEmployeeById(id);
        removeEmployee.setStatus(false);
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeList.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public Employee updateEmployee(long id, Employee updateInformation) {
        Employee updateEmployee = getEmployeeById(id);
        if (updateEmployee == null) {
            return null;
        }
        updateEmployee.setName(updateInformation.getName());
        updateEmployee.setGender(updateInformation.getGender());
        updateEmployee.setAge(updateInformation.getAge());
        updateEmployee.setSalary(updateInformation.getSalary());
        updateEmployee.setStatus(updateInformation.getStatus());

        return updateEmployee;
    }

}
