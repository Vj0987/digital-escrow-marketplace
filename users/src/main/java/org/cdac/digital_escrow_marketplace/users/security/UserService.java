package org.cdac.digital_escrow_marketplace.users.security;

import java.util.Collection;
import java.util.List;

import org.cdac.digital_escrow_marketplace.users.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.micrometer.common.lang.Nullable;

public class UserService implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	final Users user;
	public UserService(Users user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(user.getRole()));
	}

	@Override
	public @Nullable String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}
	
}
