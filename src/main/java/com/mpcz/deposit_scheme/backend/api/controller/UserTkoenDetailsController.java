package com.mpcz.deposit_scheme.backend.api.controller;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mpcz.deposit_scheme.backend.api.jwt.security.JwtTokenUtil;
import com.mpcz.deposit_scheme.backend.api.jwt.security.JwtUser;
import com.mpcz.deposit_scheme.backend.api.repository.UserRepository;


@RestController
@RequestMapping("/user-tkoen")
public class UserTkoenDetailsController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@GetMapping("/token")
	public void saveToken(HttpServletRequest request) {
//		userRepository.findAll().stream().forEach(a -> {
			System.err.println("Test@1234");
			System.err.println("$2a$10$TotQqhI/CypAVRZYB5KZEu1zoy6aJ930m/P7LYPagn3ka5pVku/Pu");
			


//			Authentication authentication = authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(a.getUserId() + ":USER", a.));
//			final JwtUser userDetails = (JwtUser) authentication.getPrincipal();
//
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//			final String token = jwtTokenUtil.generateToken(null, userDetails, request);
//			System.err.println("Token get : " + token + "Time : " + LocalDateTime.now());

//		});

	}

}
