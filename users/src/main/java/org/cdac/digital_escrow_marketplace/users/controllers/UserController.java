package org.cdac.digital_escrow_marketplace.users.controllers;

import java.util.List;

import org.cdac.digital_escrow_marketplace.users.dto.LoginRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.RegisterRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.UserResponseDTO;
import org.cdac.digital_escrow_marketplace.users.security.JwtUtil;
import org.cdac.digital_escrow_marketplace.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public boolean registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) {

		return userService.registerUser(registerRequestDTO);
	}

	@GetMapping("/authenticate/userByEmail/{email}")
	public UserResponseDTO getUser(@PathVariable("email") String email) {
		return userService.getUser(email);
	}

	@GetMapping("/authenticate/userById/{user_id}")
	public UserResponseDTO getUser(@PathVariable("user_id") int user_id) {
		return userService.getUser(user_id);
	}

	/*@PostMapping("/authenticate/login")
	public boolean login(@RequestBody LoginRequestDTO loginRequestDTO) {
		return userService.login(loginRequestDTO);
	}*/

	@GetMapping("/authenticate/allUsers")
	public List<UserResponseDTO> allUsers() {
		return userService.allUsers();
	}

	@PostMapping("/login")
	public ResponseEntity<String> getToken(@RequestBody LoginRequestDTO loginRequestDTO) {
		return userService.login(loginRequestDTO);
	}
}
