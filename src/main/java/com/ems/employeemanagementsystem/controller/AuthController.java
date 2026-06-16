package com.ems.employeemanagementsystem.controller;

import com.ems.employeemanagementsystem.dto.LoginRequest;
import com.ems.employeemanagementsystem.dto.LoginResponse;
import com.ems.employeemanagementsystem.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        String token =
                JwtUtil.generateToken(request.getUsername());

        return new LoginResponse(token);
    }
}