package com.ems.employeemanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ems.employeemanagementsystem.dto.EmployeeDto;
import com.ems.employeemanagementsystem.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
@Tag(
        name = "Employee Management APIs",
        description = "CRUD Operations for Employee Management System"
)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // CREATE EMPLOYEE
    @Operation(
            summary = "Create Employee",
            description = "Create a new employee and save into database"
    )
    @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(
            @Valid @RequestBody EmployeeDto employeeDto) {

        EmployeeDto savedEmployee =
                employeeService.saveEmployee(employeeDto);

        return new ResponseEntity<>(
                savedEmployee,
                HttpStatus.CREATED
        );
    }

    // GET ALL EMPLOYEES WITH PAGINATION AND SORTING
    @GetMapping
    public ResponseEntity<Page<EmployeeDto>> getAllEmployees(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return ResponseEntity.ok(
                employeeService.getAllEmployees(
                        page,
                        size,
                        sortBy,
                        sortDir
                )
        );
    }

    // GET EMPLOYEE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(id)
        );
    }

    // UPDATE EMPLOYEE
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDto employeeDto) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(
                        id,
                        employeeDto
                )
        );
    }

    // DELETE EMPLOYEE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok(
                "Employee deleted successfully"
        );
    }

    // SEARCH EMPLOYEES
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> searchEmployees(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                employeeService.searchEmployees(keyword)
        );
    }

    // FIND EMPLOYEES BY FIRST NAME
    @GetMapping("/firstname")
    public ResponseEntity<List<EmployeeDto>>
    getEmployeesByFirstName(
            @RequestParam String firstName) {

        return ResponseEntity.ok(
                employeeService.getEmployeesByFirstName(
                        firstName
                )
        );
    }

    // FIND EMPLOYEE BY EMAIL
    @GetMapping("/email")
    public ResponseEntity<EmployeeDto>
    getEmployeeByEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeService.getEmployeeByEmail(
                        email
                )
        );
    }

    // JPQL QUERY
    @GetMapping("/jpql")
    public ResponseEntity<List<EmployeeDto>>
    getEmployeesByFirstNameJPQL(
            @RequestParam String firstName) {

        return ResponseEntity.ok(
                employeeService
                        .getEmployeesByFirstNameJPQL(
                                firstName
                        )
        );
    }
}