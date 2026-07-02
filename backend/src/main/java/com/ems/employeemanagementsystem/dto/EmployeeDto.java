package com.ems.employeemanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmployeeDto {

    private Long id;

    @NotBlank(message = "First name should not be empty")
    private String firstName;

    @NotBlank(message = "Last name should not be empty")
    private String lastName;

    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email format is invalid")
    private String email;

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}