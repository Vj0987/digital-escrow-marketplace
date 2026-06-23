package org.cdac.digital_escrow_marketplace.users.services;

import java.util.List;

import org.cdac.digital_escrow_marketplace.users.dto.LoginRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.RegisterRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
	public boolean registerUser(RegisterRequestDTO registerRequestDTO);
	public UserResponseDTO getUser(String email);
	public UserResponseDTO getUser(int id);
	public List<UserResponseDTO> allUsers ();
	public ResponseEntity<String> login(LoginRequestDTO loginRequestDTO);
}
