package com.app.ss.domain;

import org.springframework.security.core.GrantedAuthority;

public class Authorities implements GrantedAuthority {

	private static final long serialVersionUID = -55682818433331453L;

	private String username;
	private String authority;

	
	public Authorities(String username, String authority) {
		this.username = username;
		this.authority = authority;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}

}
