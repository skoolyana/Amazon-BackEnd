package com.dailycodebuffer.springboot.tutorial.app.jwt.util.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import com.dailycodebuffer.springboot.tutorial.app.jwt.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtilImpl implements JwtUtil {

    @Value("${jwt.secret}")
	private static final String SECRET_KEY = "secret";
	
	
	
	@Override
	public String extractUsername(String token) {
		// TODO Auto-generated method stub
		return this.extractClaims(token, Claims::getSubject);
	}

	@Override
	public Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return this.extractClaims(token, Claims::getExpiration);
	}

	@Override
	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = this.extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(final String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(final String token) {
		return this.extractExpiration(token).before(new Date());
	}
	
	

	@Override
	public String generateToken(UserDetails userDetails) {

		final Map<String, Object> claims = new HashMap<>();
		return this.createToken(claims, userDetails.getUsername());
	}
	
	private String createToken(final Map<String, Object> claims, final String subject) {
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(subject)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
					.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
		.compact();
	}

	@Override
	public Boolean validateToken(final String token, final UserDetails userDetails) {
		final String username = this.extractUsername(token);
		return (
			username.equals(userDetails.getUsername()) && !isTokenExpired(token)
		);
	}
	

	
	
}
