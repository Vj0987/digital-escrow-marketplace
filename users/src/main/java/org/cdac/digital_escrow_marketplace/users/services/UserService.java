package org.cdac.digital_escrow_marketplace.users.services;

import java.util.List;

import org.cdac.digital_escrow_marketplace.users.dto.LoginRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.LoginResponseDTO;
import org.cdac.digital_escrow_marketplace.users.dto.RegisterRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.ResetPasswordRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.UpdateProfileRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

	public boolean registerClient(RegisterRequestDTO registerRequestDTO);

	public boolean registerProvider(RegisterRequestDTO registerRequestDTO);

	public UserResponseDTO getUser(String email);

	public UserResponseDTO getUser(int userId);

	public List<UserResponseDTO> allUsers();

	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

	public UserResponseDTO updateUserProfile(int userId, UpdateProfileRequestDTO updateProfileRequestDTO);

	public void forgotPassword(String email);

	public String changePassword(ResetPasswordRequestDTO resetPasswordRequestDTO);
}