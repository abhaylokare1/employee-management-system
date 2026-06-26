package com.ems.employeemanagementsystem.service;

import com.ems.employeemanagementsystem.dto.EmployeeDto;
import com.ems.employeemanagementsystem.entity.Employee;
import com.ems.employeemanagementsystem.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ems.employeemanagementsystem.exception.ResourceNotFoundException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testGetEmployeeById() {

        // Arrange
        Employee employee = new Employee();

        employee.setId(1L);
        employee.setFirstName("Abhay");
        employee.setLastName("Lokare");
        employee.setEmail("abhay@gmail.com");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        // Act
        EmployeeDto dto =
                employeeService.getEmployeeById(1L);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Abhay", dto.getFirstName());
        assertEquals("Lokare", dto.getLastName());
        assertEquals("abhay@gmail.com", dto.getEmail());
    }
    @Test
    void testDeleteEmployee() {

        Long id = 1L;

        doNothing().when(employeeRepository)
                .deleteById(id);

        String result =
                employeeService.deleteEmployee(id);

        assertEquals(
                "Employee deleted successfully",
                result
        );

        verify(employeeRepository)
                .deleteById(id);
    }
    @Test
    void testSaveEmployee() {

        // Arrange
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Abhay");
        employeeDto.setLastName("Lokare");
        employeeDto.setEmail("abhay@gmail.com");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Abhay");
        employee.setLastName("Lokare");
        employee.setEmail("abhay@gmail.com");

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);

        // Act
        EmployeeDto savedEmployee =
                employeeService.saveEmployee(employeeDto);

        // Assert
        assertNotNull(savedEmployee);
        assertEquals(1L, savedEmployee.getId());
        assertEquals("Abhay", savedEmployee.getFirstName());
        assertEquals("Lokare", savedEmployee.getLastName());
        assertEquals("abhay@gmail.com", savedEmployee.getEmail());

        verify(employeeRepository)
                .save(any(Employee.class));
    }
    @Test
    void testUpdateEmployee() {

        // Arrange
        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        existingEmployee.setFirstName("Abhay");
        existingEmployee.setLastName("Lokare");
        existingEmployee.setEmail("abhay@gmail.com");

        EmployeeDto updatedDto = new EmployeeDto();
        updatedDto.setFirstName("Abhay");
        updatedDto.setLastName("Lokare Updated");
        updatedDto.setEmail("abhay123@gmail.com");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(existingEmployee));

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(existingEmployee);

        // Act
        EmployeeDto result =
                employeeService.updateEmployee(1L, updatedDto);

        // Assert
        assertNotNull(result);
        assertEquals("Abhay", result.getFirstName());
        assertEquals("Lokare Updated", result.getLastName());
        assertEquals("abhay123@gmail.com", result.getEmail());

        verify(employeeRepository)
                .findById(1L);

        verify(employeeRepository)
                .save(any(Employee.class));
    }
    @Test
    void testGetEmployeeById_NotFound() {

        // Arrange
        when(employeeRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act + Assert
        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> employeeService.getEmployeeById(999L)
                );

        assertEquals(
                "Employee not found with id: 999",
                exception.getMessage()
        );

        verify(employeeRepository)
                .findById(999L);
    }
    @Test
    void testGetAllEmployees() {

        // Arrange
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("Abhay");
        employee1.setLastName("Lokare");
        employee1.setEmail("abhay@gmail.com");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Rahul");
        employee2.setLastName("Sharma");
        employee2.setEmail("rahul@gmail.com");

        List<Employee> employeeList =
                Arrays.asList(employee1, employee2);

        Pageable pageable =
                PageRequest.of(0, 5);

        Page<Employee> employeePage =
                new PageImpl<>(
                        employeeList,
                        pageable,
                        employeeList.size()
                );

        when(employeeRepository.findAll(any(Pageable.class)))
                .thenReturn(employeePage);

        // Act
        Page<EmployeeDto> result =
                employeeService.getAllEmployees(
                        0,
                        5,
                        "id",
                        "asc"
                );

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());

        assertEquals(
                "Abhay",
                result.getContent().get(0).getFirstName()
        );

        assertEquals(
                "Rahul",
                result.getContent().get(1).getFirstName()
        );

        verify(employeeRepository)
                .findAll(any(Pageable.class));
    }
}