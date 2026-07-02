package com.ems.employeemanagementsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeManagementOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Employee Management System API")
                                .description(
                                        "REST APIs for Employee Management System built using Spring Boot"
                                )
                                .version("1.0")
                                .contact(
                                        new Contact()
                                                .name("Abhay Lokare")
                                                .email("abhaylokare123@gmail.com")
                                )
                );
    }
}