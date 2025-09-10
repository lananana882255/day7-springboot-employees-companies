package com.example.SpringBootDemo.Service;

import com.example.SpringBootDemo.Employee;
import com.example.SpringBootDemo.Repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp(){
        employeeService.clearEmployees();
    }

    @Test
    public void should_set_employee_status_active_when_create_given_an_valid_employee() throws EmployeeNotCreatedWithInvalidArgumentsException, EmployeeNotFoundException {
        Employee employee=new Employee();
        employee.setAge(21);
        employee.setSalary(18000.00);
        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);

        doAnswer(invocation -> {
            Employee savedEmployee = invocation.getArgument(0);
            savedEmployee.setId(1);
            return null;
        }).when(employeeRepository).save(any(Employee.class));
        Map<String,Long> result=employeeService.create(employee);
        assertEquals(1,result.get("id"));
        verify(employeeRepository, times(1)).save(employeeCaptor.capture());
        Employee savedEmployee = employeeCaptor.getValue();
        assertTrue(savedEmployee.getStatus());
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
    public void should_set_employee_status_negative_when_delete_given_an_valid_employee_id() throws EmployeeNotCreatedWithInvalidArgumentsException, EmployeeNotFoundException, EmployeeAlreadyDeletedException {
        Employee employee=new Employee();
        employee.setId(1);
        employee.setAge(21);
        employee.setSalary(18000.00);
        employee.setStatus(true);
        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);
        doCallRealMethod().when(employeeRepository).delete(1);
        assertTrue(employeeService.delete(1));
        assertFalse( employee.getStatus());
        verify(employeeRepository,times(1)).delete(1);
    }

    @Test
    public void should_return_EmployeeNotFoundException_when_delete_given_an_invalid_employee_id() throws EmployeeNotCreatedWithInvalidArgumentsException, EmployeeNotFoundException, EmployeeAlreadyDeletedException {
        when(employeeRepository.delete(1)).thenReturn(false);
        assertThrows(EmployeeNotFoundException.class,()->{
            employeeService.delete(1);
        });
        verify(employeeRepository,times(1)).delete(1);
    }

    @Test
    public void should_return_EmployeeAlreadyDeletedException_when_delete_given_already_deleted_employee_id() throws EmployeeNotCreatedWithInvalidArgumentsException, EmployeeNotFoundException, EmployeeAlreadyDeletedException {
        Employee deletedEmployee = new Employee();
        deletedEmployee.setId(1);
        deletedEmployee.setStatus(false);
        when(employeeRepository.getEmployeeById(1)).thenReturn(deletedEmployee);
        doThrow(new EmployeeAlreadyDeletedException("Employee already deleted."))
                .when(employeeRepository).delete(1);
        assertThrows(EmployeeAlreadyDeletedException.class,()->{
            employeeService.delete(1);
        });
        verify(employeeRepository,times(1)).delete(1);
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
    public void should_throw_EmployeeNotFoundException_when_get_employee_given_an_invalid_employee_id() {
        when(employeeRepository.getEmployeeById(3)).thenReturn(null);
        assertThrows(EmployeeNotFoundException.class,()->employeeService.getEmployeeById(3));
        verify(employeeRepository,times(1)).getEmployeeById(3);
    }

    @Test
    public void should_return_updateEmployee_when_update_given_valid_employee_id_and_updateImformation() throws EmployeeAlreadyDeletedException, EmployeeNotFoundException {
        Map<String, Object> updateInformation = new HashMap<>();
        updateInformation.put("name", "Tony");
        updateInformation.put("age", 30);
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(1);
        updatedEmployee.setName("Tony");
        updatedEmployee.setAge(30);
        updatedEmployee.setStatus(true);

        when(employeeRepository.updateEmployee(1, updateInformation)).thenReturn(updatedEmployee);
        Employee result = employeeService.updateEmployee(1, updateInformation);
        assertNotNull(result);
        assertEquals("Tony", result.getName());
        assertEquals(30, result.getAge());
        assertTrue(result.getStatus());
        verify(employeeRepository, times(1)).updateEmployee(1, updateInformation);
    }

    @Test
    public void should_return_404_when_update_given_invalid_employee_id_and_updateImformation(){
        Map<String, Object> updateInformation = new HashMap<>();
        updateInformation.put("name", "Tony");

        when(employeeRepository.updateEmployee(1, updateInformation)).thenReturn(null);
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.updateEmployee(1, updateInformation);
        });
        verify(employeeRepository, times(1)).updateEmployee(anyLong(), anyMap());
    }

    @Test
    public void should_return_400_when_update_given_valid_employee_id_but_status_is_negative_and_updateImformation(){
        Map<String, Object> updateInformation = new HashMap<>();
        updateInformation.put("name", "Tony");
        Employee deletedEmployee = new Employee();
        deletedEmployee.setId(1);
        deletedEmployee.setStatus(false);
        when(employeeRepository.updateEmployee(1,updateInformation)).thenReturn(deletedEmployee);
        assertThrows(EmployeeAlreadyDeletedException.class, () -> {
            employeeService.updateEmployee(1, updateInformation);
        });

        verify(employeeRepository, times(1)).updateEmployee(1,updateInformation);
    }
}