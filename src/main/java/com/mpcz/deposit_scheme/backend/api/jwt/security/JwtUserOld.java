//package com.mpcz.deposit_scheme.backend.api.jwt.security;
//
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
//
//public class JwtUserOld implements UserDetails {
//
//	 
//	private static final long serialVersionUID = 1L;
//	private final Long id;
//	private final String username;
//	private final String password;
//	private final Consumer consumer;
//	private final Boolean isActive;
// 
//	private final Collection<? extends GrantedAuthority> authorities;
//
//	public JwtUser(Long id, String username, String password, Consumer consumer, Boolean enabled,
//			Collection<? extends GrantedAuthority> authorities) {
//		super();
//		this.id = id;
//		this.username = username;
//		this.password = password;
//		this.consumer = consumer;
//		this.isActive = enabled;
//		this.authorities = authorities;
//
// 
//
//	}
//
//	@Override
//	public String getPassword() {
//
//		return password;
//	}
//
//	@Override
//	public String getUsername() {
//		return username;
//	}
//
//	@JsonIgnore
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@JsonIgnore
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@JsonIgnore
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	public Boolean getIsActive() {
//		return isActive;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return authorities;
//	}
//
//	@JsonIgnore
//	public Long getId() {
//		return id;
//	}
//
//	public Consumer getConsumer() {
//		return consumer;
//	}
//
// 
//
//	@Override
//	public String toString() {
//		return "JwtUser [id=" + id + ", username=" + username + ", +password=" + password + ", consumer=" + consumer
//				+ ", isActive=" + isActive + ", authorities=" + authorities + "]";
//	}
//
//	@Override
//	public boolean isEnabled() {
//		if (true == isActive) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//}
