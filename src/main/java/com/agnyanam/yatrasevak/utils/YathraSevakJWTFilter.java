package com.agnyanam.yatrasevak.utils;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.agnyanam.yatrasevak.exception.YathraSevakErrorResponse;
import com.agnyanam.yatrasevak.service.YathriSevakJWTUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;

@Configuration
public class YathraSevakJWTFilter extends OncePerRequestFilter {

	@Autowired
	YathraSevakJwtTokenUtil jwtTokenUtil;

	@Autowired
	YathriSevakJWTUserDetailsService jwtUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String path = request.getRequestURI();
		if (path.equals("/yatra/login") || path.equals("/yatra/signup")) {
			chain.doFilter(request, response);
			return;
		}

		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);

			try {

				username = jwtTokenUtil.getUsernameFromToken(jwtToken);

			}catch (ExpiredJwtException e) {

				YathraSevakErrorResponse errResponse = new YathraSevakErrorResponse(
						YathraSevakConstants.YATHRI_AUTHENTICATION_ERROR_CODE,
						YathraSevakConstants.YATHRI_AUTHENTICATION_ERROR_JWTEXPIRED);
				ObjectMapper mapper = new ObjectMapper();
				response.setStatus(HttpStatus.FORBIDDEN.value());
				response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
				response.getWriter().write(mapper.writeValueAsString(errResponse));
				return;
			} 
			catch (Exception e) {

				YathraSevakErrorResponse errResponse = new YathraSevakErrorResponse(
						YathraSevakConstants.YATHRI_AUTHENTICATION_ERROR_CODE,
						YathraSevakConstants.YATHRI_AUTHENTICATION_ERROR_INVALIDJWT);
				ObjectMapper mapper = new ObjectMapper();
				response.setStatus(HttpStatus.FORBIDDEN.value());
				response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
				response.getWriter().write(mapper.writeValueAsString(errResponse));
				return;

			} 

		} else {
			YathraSevakErrorResponse errResponse = new YathraSevakErrorResponse(
					YathraSevakConstants.YATHRI_AUTHENTICATION_ERROR_CODE,
					YathraSevakConstants.YATHRI_AUTHENTICATION_ERROR_AUTHORIZATION_HEADER_NOT_SUPPLIED);
			ObjectMapper mapper = new ObjectMapper();
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write(mapper.writeValueAsString(errResponse));
			return;
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails.getUsername())) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				YathraSevakErrorResponse errResponse = new YathraSevakErrorResponse(
						YathraSevakConstants.YATHRI_AUTHENTICATION_ERROR_CODE,
						YathraSevakConstants.YATHRI_AUTHENTICATION_ERROR_JWTEXPIRED);
				ObjectMapper mapper = new ObjectMapper();
				response.setStatus(HttpStatus.FORBIDDEN.value());
				response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
				response.getWriter().write(mapper.writeValueAsString(errResponse));
				return;
			}
		} else {
			YathraSevakErrorResponse errResponse = new YathraSevakErrorResponse(
					YathraSevakConstants.YATHRI_AUTHENTICATION_ERROR_CODE,
					YathraSevakConstants.USERNAME_NOT_FOUND_MESSAGE);
			ObjectMapper mapper = new ObjectMapper();
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write(mapper.writeValueAsString(errResponse));
			return;
		}
		chain.doFilter(request, response);
	}
}