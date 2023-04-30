package com.agnyanam.yatrasevak;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.agnyanam.yatrasevak.utils.YathraSevakJWTFilter;


@SpringBootApplication
@EnableWebSecurity
public class YatrasevakApplication {

	@Autowired
	YathraSevakJWTFilter filter;
	
	public static void main(String[] args) {
		SpringApplication.run(YatrasevakApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4, new SecureRandom());
	}
	
	@Bean                                                            
	public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests().antMatchers("/yatra/login","/yatra/signup").permitAll()
					.anyRequest().authenticated().and().
						addFilterBefore(filter,BasicAuthenticationFilter.class).
						exceptionHandling().and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		return http.build();
	}

}
