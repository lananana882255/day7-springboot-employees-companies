package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EmployeeJPARepository extends JpaRepository<Employee, Long> {
    Employee save(Employee updateEmployee);

    List<Employee> findByGender(String gender);
}
