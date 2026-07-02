package com.ems.employeemanagementsystem.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {

		Optional<User> userOptional =
				userRepository.findByUsername(request.getUsername());

		if (userOptional.isEmpty()) {
			throw new RuntimeException("User not found");
		}

		User user = userOptional.get();

		if (!passwordEncoder.matches(
				request.getPassword(),
				user.getPassword())) {

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