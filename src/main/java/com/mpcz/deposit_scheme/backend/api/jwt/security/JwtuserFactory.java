package com.mpcz.deposit_scheme.backend.api.jwt.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.User;

public class JwtuserFactory {

	public static JwtConsumer create(Consumer consumer) {
		return new JwtConsumer(consumer.getConsumerId(), consumer.getConsumerLoginId(),
				consumer.getConsumerCredentials(), consumer, consumer.isActive(),
				maptoGrantedAuthorities(new ArrayList<String>(Arrays.asList("ROLE_ADMIN"))),"CONSUMER");
	}

	public static JwtUser create(User user) {
		return new JwtUser(user.getAdUserId(), user.getUserId(), user.getUserCredentials(), user, user.isActive(),
				maptoGrantedAuthorities(new ArrayList<String>(Arrays.asList("ROLE_ADMIN"))),"USER");
	}

	private static List<GrantedAuthority> maptoGrantedAuthorities(List<String> authorities) {

		return authorities.stream().map(Authority -> new SimpleGrantedAuthority(Authority))
				.collect(Collectors.toList());
	}

}
