package com.ems.employeemanagementsystem.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ems.employeemanagementsystem.dto.LoginRequest;
import com.ems.employeemanagementsystem.dto.LoginResponse;
import com.ems.employeemanagementsystem.entity.User;
import com.ems.employeemanagementsystem.repository.UserRepository;
import com.ems.employeemanagementsystem.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {

		Optional<User> userOptional =
				userRepository.findByUsername(request.getUsername());

		if (userOptional.isEmpty()) {
			throw new RuntimeException("User not found");
		}

		User user = userOptional.get();

		if (!user.getPassword().equals(request.getPassword())) {
			throw new RuntimeException("Invalid password");
		}

		String token =
				JwtUtil.generateToken(
						user.getUsername(),
						user.getRole().name()
				);

		return new LoginResponse(token);
	}
}
//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJST0xFX0FETUlOIiwiaWF0IjoxNzgxNjc5MDAyLCJleHAiOjE3ODE3NjU0MDJ9.vTBKj40Nq9vNq_Y78dw2gUvmMADZKj67mHuV9pauH_A

//user
//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTc4MTY3OTAyOSwiZXhwIjoxNzgxNzY1NDI5fQ.OgNVrXuLTHQh_JD2nIS0Yvh0DB5lFlYSLmfec6ReT20