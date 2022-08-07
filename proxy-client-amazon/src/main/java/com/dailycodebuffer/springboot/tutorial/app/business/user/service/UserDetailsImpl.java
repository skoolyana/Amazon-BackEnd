package com.dailycodebuffer.springboot.tutorial.app.business.user.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dailycodebuffer.springboot.tutorial.app.business.user.domain.CredentialDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private final CredentialDto credential;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(this.credential.getRoleBasedAuthority().name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.credential.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.credential.getUsername();	
		
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return this.credential.getIsAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return this.credential.getIsAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return this.credential.getIsCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.credential.getIsEnabled();
	}


	
	
}
