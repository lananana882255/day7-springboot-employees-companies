package com.example.SpringBootDemo.Service;

import com.example.SpringBootDemo.Employee;
import com.example.SpringBootDemo.EmployeeNotCreatedWithInvalidArgumentsException;
import com.example.SpringBootDemo.EmployeeNotFoundException;
import com.example.SpringBootDemo.Repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void should_set_employee_status_active_when_create_given_an_valid_employee() throws EmployeeNotCreatedWithInvalidArgumentsException, EmployeeNotFoundException {
        Employee employee=new Employee();
        employee.setAge(21);
        employee.setSalary(18000.00);
        Map<String,Long> result=employeeService.create(employee);
        assertEquals(1,result.get("id"));
//        assertEquals(true,employeeService.getEmployeeById(1).getStatus());
        verify(employeeRepository,times(1)).save(any());
    }

    @Test
    public void should_not_create_employee_when_create_given_an_employee_with_age_over_30_and_salary_below_20000(){
        Employee employee=new Employee();
        employee.setId(31);
        employee.setSalary(18000.00);
        assertThrows(EmployeeNotCreatedWithInvalidArgumentsException.class,()->employeeService.create(employee));
        verify(employeeRepository,never()).save(any());
    }

    @Test
    public void should_create_employee_when_create_given_a_valid_employee() throws EmployeeNotCreatedWithInvalidArgumentsException {
        Employee employee=new Employee();
        employee.setAge(21);
        employee.setSalary(18000.00);
        Map<String,Long> result=employeeService.create(employee);
        assertEquals(1,result.get("id"));
        verify(employeeRepository,times(1)).save(any());
    }

    @Test
    public void should_not_create_employee_when_create_given_an_invalid_employee_age(){
        Employee employee=new Employee();
        employee.setId(17);
        assertThrows(EmployeeNotCreatedWithInvalidArgumentsException.class,()->employeeService.create(employee));
        verify(employeeRepository,never()).save(any());
    }

    @Test
    public void should_employee_when_get_employee_given_a_valid_employee_id() throws EmployeeNotFoundException {
        Employee employee=new Employee();
        employee.setId(1);
        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);
        Employee result=employeeService.getEmployeeById(1);
        assertEquals(1,result.getId());
        verify(employeeRepository,times(1)).getEmployeeById(1);
    }

    @Test
    public void should_throw_exception_when_get_employee_given_an_invalid_employee_id() {
        when(employeeRepository.getEmployeeById(3)).thenReturn(null);
        assertThrows(EmployeeNotFoundException.class,()->employeeService.getEmployeeById(3));
        verify(employeeRepository,times(1)).getEmployeeById(3);
    }
}