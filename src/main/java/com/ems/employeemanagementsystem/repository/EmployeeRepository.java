package com.ems.employeemanagementsystem.repository;

import com.ems.employeemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByFirstName(String firstName);

    List<Employee> findByFirstNameContainingIgnoreCase(String keyword);
    Optional<Employee> findByEmail(String email);
    @Query("SELECT e FROM Employee e WHERE e.firstName = :firstName")
    List<Employee> findEmployeesByFirstNameJPQL(
            @Param("firstName") String firstName);

}