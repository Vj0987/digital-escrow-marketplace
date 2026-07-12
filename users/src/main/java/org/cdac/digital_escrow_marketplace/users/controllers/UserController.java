package org.cdac.digital_escrow_marketplace.users.controllers;

import java.util.List;

import org.cdac.digital_escrow_marketplace.users.dto.LoginRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.LoginResponseDTO;
import org.cdac.digital_escrow_marketplace.users.dto.RegisterRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.ResetPasswordRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.UpdateProfileRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.UserResponseDTO;
import org.cdac.digital_escrow_marketplace.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register/client")
	public ResponseEntity<Boolean> registerClient(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {

		return ResponseEntity.ok(userService.registerClient(registerRequestDTO));
	}

	@PostMapping("/register/provider")
	public ResponseEntity<Boolean> registerProvider(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {

		return ResponseEntity.ok(userService.registerProvider(registerRequestDTO));
	}

	@GetMapping("/userByEmail/{email}")
	public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {

		return ResponseEntity.ok(userService.getUser(email));
	}

	@GetMapping("/userById/{userId}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int userId) {

		return ResponseEntity.ok(userService.getUser(userId));
	}

	@GetMapping("/allUsers")
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

		return ResponseEntity.ok(userService.allUsers());
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

		return ResponseEntity.ok(userService.login(loginRequestDTO));
	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<UserResponseDTO> updateUserProfile(@PathVariable int userId,
			@Valid @RequestBody UpdateProfileRequestDTO updateProfileRequestDTO) {

		return ResponseEntity.ok(userService.updateUserProfile(userId, updateProfileRequestDTO));
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam String email) {

		userService.forgotPassword(email);

		return ResponseEntity.ok("OTP sent successfully.");
	}

	@PostMapping("/change-password")
	public ResponseEntity<String> changePassword(@Valid @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {

		return ResponseEntity.ok(userService.changePassword(resetPasswordRequestDTO));
	}

}