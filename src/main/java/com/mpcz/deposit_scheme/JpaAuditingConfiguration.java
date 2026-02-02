package com.mpcz.deposit_scheme;

import java.util.Optional;
import java.util.OptionalLong;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableJpaAuditing 
@Configuration
public class JpaAuditingConfiguration {

	@Bean
	public AuditorAware<Long> createAuditorProvider() {
		return new SecurityAuditor();
	}

	@Bean
	public AuditingEntityListener createAuditingListener() {
		return new AuditingEntityListener();
	}

	public static class SecurityAuditor implements AuditorAware<Long> {
		@Override
		public Optional<Long> getCurrentAuditor() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

//	            String username = auth.getName();
//	            System.out.println("USername "+username);
			String username = "1";

//	            String username = "";
			if (auth != null) {
				username = auth.getName();
			} else {
				username = "1";
			}
//	            System.out.println("USername "+username);

			OptionalLong optLong = "anonymousUser".equals(username) ? OptionalLong.of(0L)
					: OptionalLong.of(Long.valueOf(username));
			Optional<Long> optional = optLong.isPresent() ? Optional.of(optLong.getAsLong()) : Optional.empty();

			return optional;
		}
	}

}
