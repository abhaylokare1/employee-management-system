package com.ems.employeemanagementsystem.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.ems.employeemanagementsystem.dto.EmployeeDto;
import com.ems.employeemanagementsystem.entity.Employee;
import com.ems.employeemanagementsystem.exception.ResourceNotFoundException;
import com.ems.employeemanagementsystem.mapper.EmployeeMapper;
import com.ems.employeemanagementsystem.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private static final Logger log =
            LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    // CREATE EMPLOYEE
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        log.info(
                "Creating employee: {} {}",
                employeeDto.getFirstName(),
                employeeDto.getLastName()
        );

        Employee employee =
                EmployeeMapper.mapToEmployee(employeeDto);

        Employee savedEmployee =
                employeeRepository.save(employee);

        log.info(
                "Employee created successfully with id: {}",
                savedEmployee.getId()
        );

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    // GET ALL EMPLOYEES WITH PAGINATION AND SORTING
    public Page<EmployeeDto> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        log.info(
                "Fetching employees. page={}, size={}, sortBy={}, sortDir={}",
                page,
                size,
                sortBy,
                sortDir
        );

        Sort sort =
                sortDir.equalsIgnoreCase("asc")
                        ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );

        Page<Employee> employees =
                employeeRepository.findAll(pageable);

        return employees.map(EmployeeMapper::mapToEmployeeDto);
    }

    // GET EMPLOYEE BY ID
    public EmployeeDto getEmployeeById(Long id) {

        log.info(
                "Fetching employee with id: {}",
                id
        );

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() -> {

                            log.error(
                                    "Employee not found with id: {}",
                                    id
                            );

                            return new ResourceNotFoundException(
                                    "Employee not found with id: " + id
                            );
                        });

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    // UPDATE EMPLOYEE
    public EmployeeDto updateEmployee(
            Long id,
            EmployeeDto updatedEmployee) {

        log.info(
                "Updating employee with id: {}",
                id
        );

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() -> {

                            log.error(
                                    "Employee not found with id: {}",
                                    id
                            );

                            return new ResourceNotFoundException(
                                    "Employee not found with id: " + id
                            );
                        });

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObj =
                employeeRepository.save(employee);

        log.info(
                "Employee updated successfully with id: {}",
                id
        );

        return EmployeeMapper.mapToEmployeeDto(
                updatedEmployeeObj
        );
    }

    // DELETE EMPLOYEE
    public String deleteEmployee(Long id) {

        log.info(
                "Deleting employee with id: {}",
                id
        );

        employeeRepository.deleteById(id);

        log.info(
                "Employee deleted successfully with id: {}",
                id
        );

        return "Employee deleted successfully";
    }

    // SEARCH EMPLOYEE BY KEYWORD
    public List<EmployeeDto> searchEmployees(
            String keyword) {

        log.info(
                "Searching employees with keyword: {}",
                keyword
        );

        List<Employee> employees =
                employeeRepository
                        .findByFirstNameContainingIgnoreCase(
                                keyword
                        );

        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .toList();
    }

    // FIND EMPLOYEES BY FIRST NAME
    public List<EmployeeDto> getEmployeesByFirstName(
            String firstName) {

        log.info(
                "Searching employees by first name: {}",
                firstName
        );

        List<Employee> employees =
                employeeRepository.findByFirstName(
                        firstName
                );

        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .toList();
    }

    // FIND EMPLOYEE BY EMAIL
    public EmployeeDto getEmployeeByEmail(
            String email) {

        log.info(
                "Searching employee by email: {}",
                email
        );

        Employee employee =
                employeeRepository.findByEmail(email)
                        .orElseThrow(() -> {

                            log.error(
                                    "Employee not found with email: {}",
                                    email
                            );

                            return new ResourceNotFoundException(
                                    "Employee not found with email: "
                                            + email
                            );
                        });

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    // JPQL QUERY
    public List<EmployeeDto>
    getEmployeesByFirstNameJPQL(
            String firstName) {

        log.info(
                "Executing JPQL query for first name: {}",
                firstName
        );

        List<Employee> employees =
                employeeRepository
                        .findEmployeesByFirstNameJPQL(
                                firstName
                        );

        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .toList();
    }
}