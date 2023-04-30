package com.agnyanam.yatrasevak.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.agnyanam.yatrasevak.model.Yathri;
import com.agnyanam.yatrasevak.resource.YathriRepository;

@Configuration
public class YathriSevakJWTUserDetailsService implements UserDetailsService{

	@Autowired
	YathriRepository yathriRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Yathri yathri = yathriRepo.findByUserName(username);
		if(yathri != null) {
			return new User(yathri.getUserName(),yathri.getPassword(),new ArrayList<>());
		}
		else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}
