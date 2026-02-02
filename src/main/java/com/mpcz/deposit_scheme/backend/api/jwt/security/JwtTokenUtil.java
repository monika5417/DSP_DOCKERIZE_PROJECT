package com.mpcz.deposit_scheme.backend.api.jwt.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.mpcz.deposit_scheme.backend.api.util.InternetProtocolAddressUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final String CLAIM_KEY_USERID = "sub";
	static final String CLAIM_KEY_AUDIENCE = "audience";
	static final String CLAIM_KEY_CREATED = "created";
	static final String CLAIM_KEY_ROLES = "roles";
	static final String CLAIM_KEY_USERNAME = "userfullname";
	static final String CLAIM_KEY_CONSUMERNAME = "consumerfullname";
	static final String IS_FIRST_LOGIN = "isFirstLogin";
	static final String IP = "ip";
	static final String UGER_AGENT = "ua";
	static final String DISCOM_NAME = "discomName";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String getUsernameFromToken(String authToken) {
		String username = null;
		try {
			final Claims claims = getClaimsFromToken(authToken);
			username = claims.getSubject();
		} catch (Exception e) {
			// e.printStackTrace();

			username = null;
		}
		return username;
	}

	private Claims getClaimsFromToken(String authToken) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
		} catch (Exception e) {
			claims = null;
		}

		return claims;
	}

	// for retrieveing any information from token we will need the secret key
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String authToken) {
		final Date expiration = getExpirationDateFromToken(authToken);
		return expiration.before(new Date());
	}

	public String generateToken(JwtConsumer consumerDetails, JwtUser userDetails, HttpServletRequest request) {
		Map<String, Object> claims = new HashMap<String, Object>();
		if (consumerDetails != null) {
			claims.put(UGER_AGENT, InternetProtocolAddressUtil.getUserAgent(request));
			claims.put(IP, InternetProtocolAddressUtil.getClientIp(request));
			claims.put(CLAIM_KEY_USERID, consumerDetails.getUsername());
			claims.put(CLAIM_KEY_CREATED, new Date());
			claims.put(CLAIM_KEY_CONSUMERNAME, consumerDetails.getConsumer().getConsumerName());
			claims.put(IS_FIRST_LOGIN, consumerDetails.getConsumer().getIsFirstLogin());
			return generateToken(claims, consumerDetails.getUsername());
		}

		if (userDetails != null) {
			claims.put(UGER_AGENT, InternetProtocolAddressUtil.getUserAgent(request));
			claims.put(IP, InternetProtocolAddressUtil.getClientIp(request));
			claims.put(CLAIM_KEY_USERID, userDetails.getUsername());
			claims.put(CLAIM_KEY_CREATED, new Date());
			claims.put(CLAIM_KEY_ROLES, userDetails.getRoles());
			claims.put(CLAIM_KEY_USERNAME, userDetails.getUser().getUserName());
			claims.put(IS_FIRST_LOGIN, userDetails.getUser().getIsFirstLogin());
			return generateToken(claims, userDetails.getUsername());

		}
		return null;

	}

	public String generateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	private Date getExpirationDateFromToken(String authToken) {

		Date expiration = null;
		final Claims claims = getClaimsFromToken(authToken);
		if (claims != null) {
			expiration = claims.getExpiration();
		} else {
			expiration = null;
		}
		return expiration;
	}

	public boolean validateToken(String authToken, UserDetails userDetails, String userType) {
		
		/*************** vivek 16-10-2022 *********************************/

		JwtUser user = null;
		JwtConsumer consumer = null;

		final String username = getUsernameFromToken(authToken);

		if (userType.equals("USER")) {
			user = (JwtUser) userDetails;

			return (username.equals(user.getUsername()) && !isTokenExpired(authToken));

		} else if (userType.equals("CONSUMER")) {
			consumer = (JwtConsumer) userDetails;
			return (username.equals(consumer.getUsername()) && !isTokenExpired(authToken));

		}
		
		return false;
		/*************** vivek 16-10-2022 *********************************/

	}

}
