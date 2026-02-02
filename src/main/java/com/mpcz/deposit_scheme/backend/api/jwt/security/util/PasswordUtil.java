package com.mpcz.deposit_scheme.backend.api.jwt.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
	public static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public static String getPasswordHash(String password) {
		return encoder.encode(password);
	}

}
