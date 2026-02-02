package com.mpcz.deposit_scheme.backend.api.jwt.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mpcz.deposit_scheme.backend.api.domain.User;

public class JwtUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Long id;
	private final String username;
	private final String password;
	private final User user;
	private final Boolean isActive;
	private final List<String> roles;
	private final Collection<? extends GrantedAuthority> authorities;
	private final String userType;

	public JwtUser(Long id, String username, String password, User user, Boolean enabled,
			Collection<? extends GrantedAuthority> authorities,String userType) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.user = user;
		this.isActive = enabled;
		this.authorities = authorities;
		this.userType= userType;
		//get only name of role create list of string using stream
		this.roles = user.getUserRoles().stream().map(u -> u.getRoleCode()).collect(Collectors.toList());

	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}
	
	

	public String getUserType() {
		return userType;
	}

	@Override
	public String toString() {
		return "JwtUser [id=" + id + ", username=" + username + ", +password=" + password + ", user=" + user
				+ ", isActive=" + isActive + ", authorities=" + authorities + "]";
	}

	@Override
	public boolean isEnabled() {
		if (true == isActive) {
			return true;
		} else {
			return false;
		}
	}

}
