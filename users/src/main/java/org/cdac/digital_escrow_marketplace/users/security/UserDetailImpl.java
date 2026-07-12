package org.cdac.digital_escrow_marketplace.users.security;

import org.cdac.digital_escrow_marketplace.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		return userRepository.findByEmail(email)
		        .map(CustomUserDetails::new)
		        .orElseThrow(() ->
		                new UsernameNotFoundException("User not found with email: " + email));
	}
}