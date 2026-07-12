package org.cdac.digital_escrow_marketplace.users.services;

import java.time.LocalDateTime;
import java.util.List;

import org.cdac.digital_escrow_marketplace.users.dto.LoginRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.LoginResponseDTO;
import org.cdac.digital_escrow_marketplace.users.dto.RegisterRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.ResetPasswordRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.UpdateProfileRequestDTO;
import org.cdac.digital_escrow_marketplace.users.dto.UserResponseDTO;
import org.cdac.digital_escrow_marketplace.users.entity.Otp;
import org.cdac.digital_escrow_marketplace.users.entity.Users;
import org.cdac.digital_escrow_marketplace.users.enums.Role;
import org.cdac.digital_escrow_marketplace.users.enums.UserStatus;
import org.cdac.digital_escrow_marketplace.users.exceptions.EmailAlreadyExistsException;
import org.cdac.digital_escrow_marketplace.users.exceptions.InvalidOTPException;
import org.cdac.digital_escrow_marketplace.users.exceptions.OtpAlreadyUsedException;
import org.cdac.digital_escrow_marketplace.users.exceptions.OtpExpiredException;
import org.cdac.digital_escrow_marketplace.users.exceptions.OtpNotFoundException;
import org.cdac.digital_escrow_marketplace.users.exceptions.UserNotFoundException;
import org.cdac.digital_escrow_marketplace.users.otp.OtpGenerator;
import org.cdac.digital_escrow_marketplace.users.repository.OtpRepository;
import org.cdac.digital_escrow_marketplace.users.repository.UserRepository;
import org.cdac.digital_escrow_marketplace.users.security.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	OtpRepository otpRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtUtil;

	private boolean register(RegisterRequestDTO dto, Role role) {

		if (userRepository.findByEmail(dto.getEmail()).isPresent())
			throw new EmailAlreadyExistsException("Email Already Registered");

		Users user = new Users();

		BeanUtils.copyProperties(dto, user);

		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(role);
		user.setAccountStatus(UserStatus.PENDING_VALIDATION);

		userRepository.save(user);

		return true;
	}

	@Override
	public boolean registerClient(RegisterRequestDTO dto) {
		return register(dto, Role.CLIENT);
	}

	@Override
	public boolean registerProvider(RegisterRequestDTO dto) {
		return register(dto, Role.PROVIDER);
	}

	@Override
	public UserResponseDTO getUser(String email) {

		Users user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("No user found with email : " + email));

		UserResponseDTO dto = new UserResponseDTO();

		BeanUtils.copyProperties(user, dto);

		return dto;
	}

	@Override
	public List<UserResponseDTO> allUsers() {

	    return userRepository.findAll()
	            .stream()
	            .map(user -> {
	                UserResponseDTO dto = new UserResponseDTO();
	                BeanUtils.copyProperties(user, dto);
	                return dto;
	            })
	            .toList();
	}

	@Override
	public UserResponseDTO getUser(int id) {

	    Users user = userRepository.findById(id)
	            .orElseThrow(() ->
	                    new UserNotFoundException("No user found with id : " + id));

	    UserResponseDTO dto = new UserResponseDTO();

	    BeanUtils.copyProperties(user, dto);

	    return dto;
	}

	@Override
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

	    authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    loginRequestDTO.getEmail(),
	                    loginRequestDTO.getPassword()));

	    Users user = userRepository.findByEmail(loginRequestDTO.getEmail())
	            .orElseThrow(() -> new UserNotFoundException("User not found"));

	    String token = jwtUtil.generateToken(
	            user.getUserId(),
	            user.getEmail(),
	            user.getRole().name());

	    LoginResponseDTO response = new LoginResponseDTO();

	    response.setToken(token);
	    response.setUserId(user.getUserId());
	    response.setFullName(user.getFullName());
	    response.setEmail(user.getEmail());
	    response.setRole(user.getRole().name());

	    return response;
	}

	@Override
	public UserResponseDTO updateUserProfile(int userId, UpdateProfileRequestDTO updateProfileRequestDTO) {
		Users objUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not Found"));

		objUser.setFullName(updateProfileRequestDTO.getFullName());
		objUser.setPhoneNo(updateProfileRequestDTO.getPhoneNo());

		Users updatedUser = userRepository.save(objUser);

		UserResponseDTO userResponseDTO = new UserResponseDTO();
		BeanUtils.copyProperties(updatedUser, userResponseDTO);

		return userResponseDTO;
	}

	@Override
	public void forgotPassword(String email) {

		userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("No account found with this email."));

		String otp = OtpGenerator.generateOtp();

		LocalDateTime now = LocalDateTime.now();

		Otp objOtp = otpRepository.findByEmail(email).orElse(new Otp());

		objOtp.setEmail(email);
		objOtp.setOtp(otp);
		objOtp.setCreatedAt(now);
		objOtp.setExpiry(now.plusMinutes(5));
		objOtp.setUsed(0);

		otpRepository.save(objOtp);

		System.out.println("OTP : " + otp);

	}

	private void verifyOtp(ResetPasswordRequestDTO rPRDTO) {

		Otp resetOtp = otpRepository.findByEmail(rPRDTO.getEmail())
				.orElseThrow(() -> new OtpNotFoundException("OTP not found"));

		if (resetOtp.getUsed() == 1)
			throw new OtpAlreadyUsedException("OTP already used.");

		if (LocalDateTime.now().isAfter(resetOtp.getExpiry()))
			throw new OtpExpiredException("OTP expired.");

		if (!resetOtp.getOtp().equals(rPRDTO.getOtp()))
			throw new InvalidOTPException("Invalid OTP.");

		resetOtp.setUsed(1);
		otpRepository.save(resetOtp);

	}

	@Override
	public String changePassword(ResetPasswordRequestDTO rPRDTO) {
		verifyOtp(rPRDTO);

		Users objUser = userRepository.findByEmail(rPRDTO.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User Not Found"));

		objUser.setPassword(passwordEncoder.encode(rPRDTO.getNewPassword()));

		userRepository.save(objUser);

		return "Password Changed Successfully";

	}

}
