package com.ems.employeemanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.employeemanagementsystem.dto.EmployeeDto;
import com.ems.employeemanagementsystem.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Employee Management APIs",
        description = "CRUD Operations for Employee Management System")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(
            summary = "Create Employee",
            description = "Create a new employee and save into database"
    )
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDto> saveEmployee(
            @Valid @RequestBody EmployeeDto employeeDto) {
        {

            EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);

            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);

        }
    }

    // GET ALL EMPLOYEES
    @GetMapping("/employees")
    public ResponseEntity<Page<EmployeeDto>> getAllEmployees(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(employeeService.getAllEmployees(page, size));

    }

    // GET EMPLOYEE BY ID
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {

        return ResponseEntity.ok(employeeService.getEmployeeById(id));

    }

    // UPDATE EMPLOYEE
    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDto employeeDto) {

        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDto));

    }

    // DELETE EMPLOYEE
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok("Employee deleted successfully");

    }
    @GetMapping("/employees/search")
    public ResponseEntity<List<EmployeeDto>> searchEmployees(

            @RequestParam String keyword) {

        return ResponseEntity.ok(
                employeeService.searchEmployees(keyword));

    }
    @GetMapping("/employees/sort")
    public ResponseEntity<List<EmployeeDto>> sortEmployees(

            @RequestParam String field) {

        return ResponseEntity.ok(
                employeeService.sortEmployees(field));

    }
    @GetMapping("/employees/firstname")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByFirstName(
            @RequestParam String firstName) {

        return ResponseEntity.ok(
                employeeService.getEmployeesByFirstName(firstName));
    }
    @GetMapping("/employees/email")
    public ResponseEntity<EmployeeDto> getEmployeeByEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeService.getEmployeeByEmail(email));

    }
    @GetMapping("/employees/jpql")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByFirstNameJPQL(
            @RequestParam String firstName) {

        return ResponseEntity.ok(
                employeeService.getEmployeesByFirstNameJPQL(firstName));
    }


}