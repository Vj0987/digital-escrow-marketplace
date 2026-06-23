package org.cdac.digital_escrow_marketplace.users.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.cdac.digital_escrow_marketplace.users.dto.LoginRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.RegisterRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.UserResponseDTO;
import org.cdac.digital_escrow_marketplace.users.entity.Users;
import org.cdac.digital_escrow_marketplace.users.repository.UserRepository;
import org.cdac.digital_escrow_marketplace.users.security.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtUtil;

	@Override
	public boolean registerUser(RegisterRequestDTO registerRequestDTO) {
		Users objUser = new Users();
		BeanUtils.copyProperties(registerRequestDTO, objUser);
		String encodedPassword = passwordEncoder.encode(registerRequestDTO.getPassword());
		objUser.setPassword(encodedPassword);
		objUser.setAccountStatus("ACTIVE");
		userRepository.save(objUser);
		return true;
	}

	@Override
	public UserResponseDTO getUser(String email) {

		Optional<Users> optUser = userRepository.findByEmail(email);
		if (optUser.isPresent()) {
			Users objUser = optUser.get();
			UserResponseDTO objUserResponseDTO = new UserResponseDTO();
			BeanUtils.copyProperties(objUser, objUserResponseDTO);
			return objUserResponseDTO;
		}

		throw new RuntimeException("No User found by this email");
	}

	

	@Override
	public List<UserResponseDTO> allUsers() {
		List<Users> allUsers = userRepository.findAll();
		ArrayList<UserResponseDTO> listUserResponseDTO = new ArrayList<UserResponseDTO>();
		for (Users objUser : allUsers) {
			UserResponseDTO objUserResponseDTO = new UserResponseDTO();
			BeanUtils.copyProperties(objUser, objUserResponseDTO);
			listUserResponseDTO.add(objUserResponseDTO);
		}
		return listUserResponseDTO;
	}

	@Override
	public UserResponseDTO getUser(int id) {
		Optional<Users> optUser = userRepository.findById(id);
		if (optUser.isPresent()) {
			Users objUser = optUser.get();
			UserResponseDTO objUserResponseDTO = new UserResponseDTO();
			BeanUtils.copyProperties(objUser, objUserResponseDTO);
			return objUserResponseDTO;
		}

		throw new RuntimeException("No User found by this User_Id");
	}

	@Override
	public ResponseEntity<String> login(LoginRequestDTO loginRequestDTO) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
		if (auth.isAuthenticated())
			return new ResponseEntity<>(jwtUtil.generateToken(loginRequestDTO.getEmail()), HttpStatusCode.valueOf(200));
		else
			return new ResponseEntity<>("0000", HttpStatusCode.valueOf(401));

	}

}
