package com.ems.employeemanagementsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ems.employeemanagementsystem.dto.EmployeeDto;
import com.ems.employeemanagementsystem.entity.Employee;
import com.ems.employeemanagementsystem.exception.ResourceNotFoundException;
import com.ems.employeemanagementsystem.mapper.EmployeeMapper;
import com.ems.employeemanagementsystem.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // CREATE EMPLOYEE
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);

    }
    public List<EmployeeDto> sortEmployees(String field) {

        List<Employee> employees =
                employeeRepository.findAll(Sort.by(Sort.Direction.ASC, field));

        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .toList();

    }

    // GET ALL EMPLOYEES
    public Page<EmployeeDto> getAllEmployees(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Employee> employees = employeeRepository.findAll(pageable);

        return employees.map(EmployeeMapper::mapToEmployeeDto);

    }

    // GET EMPLOYEE BY ID
    public EmployeeDto getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id: " + id));

        return EmployeeMapper.mapToEmployeeDto(employee);

    }

    // UPDATE EMPLOYEE
    public EmployeeDto updateEmployee(Long id, EmployeeDto updatedEmployee) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObj = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);

    }
    public List<EmployeeDto> searchEmployees(String keyword) {

        List<Employee> employees =
                employeeRepository.findByFirstNameContainingIgnoreCase(keyword);

        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .toList();

    }
    public List<EmployeeDto> getEmployeesByFirstName(String firstName) {

        List<Employee> employees =
                employeeRepository.findByFirstName(firstName);

        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .toList();
    }
    public EmployeeDto getEmployeeByEmail(String email) {

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with email: " + email));

        return EmployeeMapper.mapToEmployeeDto(employee);

    }

    // DELETE EMPLOYEE
    public String deleteEmployee(Long id) {

        employeeRepository.deleteById(id);

        return "Employee deleted successfully";

    }
    public List<EmployeeDto> getEmployeesByFirstNameJPQL(String firstName) {

        List<Employee> employees =
                employeeRepository.findEmployeesByFirstNameJPQL(firstName);

        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .toList();
    }

}