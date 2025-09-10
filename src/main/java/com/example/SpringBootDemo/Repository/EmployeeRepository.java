package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Employee;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employeeList= new ArrayList<>();
    public void save(Employee employee) {
        employeeList.add(employee);
    }

    public void clearEmployees() {
        this.employeeList.clear();
    }

    public List<Employee> getEmployees(String gender, Integer page,Integer size) {
        List<Employee> filteredEmployeesList=employeeList;
        if(gender!=null){
            filteredEmployeesList=filteredEmployeesList.stream().filter(employee -> employee.getGender().equalsIgnoreCase(gender)).collect(Collectors.toList());
        }
        if(page!=null&&size!=null){
            int start = (page - 1) * size;
            if (start >= filteredEmployeesList.size()) {
                return Collections.emptyList();
            }
            int end = Math.min(start + size, filteredEmployeesList.size());
            filteredEmployeesList=filteredEmployeesList.subList(start,end);
        }
        return filteredEmployeesList;
    }

    public boolean delete(long id) {
        return employeeList.removeIf(employee -> employee.getId()==id);
    }

    public Employee getEmployeeById(long id) {
        return employeeList.stream()
                .filter(employee -> employee.getId()==id)
                .findFirst().orElse(null);
    }

    public Employee updateEmployee(long id ,Map<String, Object>updateInformation) {
        Employee updateEmployee=getEmployeeById(id);
        if(updateEmployee==null){
            return null;
        }
        if (updateInformation.containsKey("age")) {
            updateEmployee.setAge((Integer) updateInformation.get("age"));
        }
        if (updateInformation.containsKey("name")) {
            updateEmployee.setName(updateInformation.get("name").toString());
        }
        if (updateInformation.containsKey("gender")) {
            updateEmployee.setGender(updateInformation.get("gender").toString());
        }
        if (updateInformation.containsKey("salary")) {
            updateEmployee.setSalary(Double.parseDouble(updateInformation.get("salary").toString()));
        }
        return updateEmployee;
    }
}
