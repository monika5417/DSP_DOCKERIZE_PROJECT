/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.jwt.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UserRepository;

/**
 * @author giggal
 *
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	// @Autowired private UserRepository userRepository;
	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String usernameType) throws UsernameNotFoundException {

		UserDetails details = null;

		String usernameTypeArray[] = usernameType.split(":");

		String username = usernameTypeArray[0];
		String userType = usernameTypeArray[1];

		if (userType.equals("CONSUMER")) {

			Optional<Consumer> consumer = consumerRepository.findByConsumerLoginId(username);

			if (username == null) {
				throw new UsernameNotFoundException(String.format("No Consumer found with username '%s'.", username));
			} else {
				try {
					final Consumer consumerObj = consumer.get();

					details = JwtuserFactory.create(consumerObj);
//						return details;
				} catch (Exception e) {
					System.out.println(e);
					throw e;
				}
			}

		}

		if (userType.equals("USER")) {

			Optional<User> user = userRepository.findByUserId(username);
		 

			if (username == null) {
				throw new UsernameNotFoundException(String.format("No User found with username '%s'.", username));
			} else {
				try {
					final User userObj = user.get();

					details = JwtuserFactory.create(userObj);
					return details;
				} catch (Exception e) {
					System.out.println(e);
					throw e;
				}
			}

		}

		return details;

	}

}
