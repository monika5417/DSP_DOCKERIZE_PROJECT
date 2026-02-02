package com.mpcz.deposit_scheme.backend.api.jwt.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mpcz.deposit_scheme.backend.api.jwt.security.JwtTokenUtil;
import com.mpcz.deposit_scheme.backend.api.jwt.security.exception.UnauthorizedException;
import com.mpcz.deposit_scheme.backend.api.util.InternetProtocolAddressUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
 
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader(this.tokenHeader);

		String authToken = null;
		String username = null;

		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			authToken = requestTokenHeader.substring(7);

			try {
				username = jwtTokenUtil.getUsernameFromToken(authToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			// logger.warn("JWT Token does not begin with Bearer String");
		}

		/*************** vivek 16-10-2022 starts *********************************/
		String requestUrl = request.getRequestURL().toString();
		
		String userType = null;
		/*************** vivek 16-10-2022 ends*********************************/
		
		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			/*************** vivek 16-10-2022 starts *********************************/
			UserDetails userDetails = null;
			if(requestUrl.contains("/user/")) {
				userType = "USER";
				 userDetails = this.userDetailsService.loadUserByUsername(username+":"+userType);
			}else if(requestUrl.contains("/consumer/")){
				userType = "CONSUMER";
				 userDetails = this.userDetailsService.loadUserByUsername(username+":"+userType);
			}
			
			 

			// if token is valid configure Spring Security to manually set authentication
			boolean isValid = jwtTokenUtil.validateToken(authToken, userDetails,userType);
			/*************** vivek 16-10-2022 ends*********************************/
			if (isValid) {

				final String ipAddress = InternetProtocolAddressUtil.getClientIp(request);
				// not match return error
//				if (!ipAddress.equals(jwtTokenUtil.getAllClaimsFromToken(authToken).get("ip"))) {
//					throw new UnauthorizedException("Invalid Login Request");
//				}

//				final String ua = InternetProtocolAddressUtil.getUserAgent(request);
//
//				if (!ua.equals(jwtTokenUtil.getAllClaimsFromToken(authToken).get("ua"))) {
//					throw new UnauthorizedException("Invalid Login Request");
//				}

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				/*
				 * After setting the Authentication in the context, we specify that the current
				 * user is authenticated. So it passes the Spring Security Configurations
				 * successfully.
				 */

				request.setAttribute("idValue", username);
				// System.out.println("test:"+username);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				// System.out.println("SecurityContextHolder.getContext().getAuthentication():"+SecurityContextHolder.getContext().getAuthentication().getName());
				// Save Login time with user details
			}
		}

		filterChain.doFilter(request, response);
	}
}
