package com.ems.employeemanagementsystem.mapper;

import com.ems.employeemanagementsystem.dto.EmployeeDto;
import com.ems.employeemanagementsystem.entity.Employee;

public class EmployeeMapper {

    // CONVERT ENTITY TO DTO
    public static EmployeeDto mapToEmployeeDto(Employee employee) {

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());

        return employeeDto;

    }

    // CONVERT DTO TO ENTITY
    public static Employee mapToEmployee(EmployeeDto employeeDto) {

        Employee employee = new Employee();

        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());

        return employee;

    }

}