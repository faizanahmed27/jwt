package com.jwt.example.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jwt.example.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final String SECRET_KEY = "0d74f69eafde832fc014d87cf2364afd27dfa98db96d3fbd20c329c40117cb44";

	public String extractUserName(String token) {

		return extractClaim(token, Claims::getSubject);
	}
	
	public boolean isValid(String token, UserDetails user) {
		
		String userName = extractUserName(token);
		
		return (userName.equals(user.getUsername())) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> resolver) {

		Claims claims = extractAllClaims(token);

		return resolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {

		return Jwts.parser().verifyWith(getSingingKey()).build().parseSignedClaims(token).getPayload();
	}

	public String generateToken(User user) {

		String token = Jwts.builder().subject(user.getUsername()).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)).signWith(getSingingKey())
				.compact();

		return token;
	}

	private SecretKey getSingingKey() {

		byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);

		return Keys.hmacShaKeyFor(keyBytes);
	}
}
