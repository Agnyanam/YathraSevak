package com.agnyanam.yatrasevak.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

@Component
public class YathraSevakJwtTokenUtil{

	private  Key key =null;
	
	@Value("${JWT.SecretKey}")
	String secretKey;
	
	@Value("${JWT.Token.Validity:1200}")
	long jwtTokenValidity;
	
	@PostConstruct
	void setKeyForTheJWTToken() {
		key = Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	//retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	//retrieve expiration date from jwt token
	private  Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private  <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
    //for retrieveing any information from token we will need the secret key
	private  Claims getAllClaimsFromToken(String token) {
		
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	//check if the token has expired
	private  Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	//generate token for user
	public  String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userName);
	}

	//while creating the token -
	//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   compaction of the JWT to a URL-safe string 
	private  String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(key).setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000)).compact();
	}

	//validate token
	public  Boolean validateToken(String token, String userName) {
		final String usernameFromToken = getUsernameFromToken(token);
		return (usernameFromToken.equals(userName) && !isTokenExpired(token));
	}
}
