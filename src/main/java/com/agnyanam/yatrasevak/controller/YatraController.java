package com.agnyanam.yatrasevak.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agnyanam.yatrasevak.exception.GetYathriWithUsernameException;
import com.agnyanam.yatrasevak.exception.YathraSevakLoginException;
import com.agnyanam.yatrasevak.exception.YathraSevakSignUpException;
import com.agnyanam.yatrasevak.model.Yathri;
import com.agnyanam.yatrasevak.request.YathriLoginRequest;
import com.agnyanam.yatrasevak.request.YathriSignUpRequest;
import com.agnyanam.yatrasevak.resource.YathriRepository;
import com.agnyanam.yatrasevak.response.GetYathriResponse;
import com.agnyanam.yatrasevak.response.YathriLoginResponse;
import com.agnyanam.yatrasevak.response.YathriSignUpResponse;
import com.agnyanam.yatrasevak.utils.YathraSevakConstants;
import com.agnyanam.yatrasevak.utils.YathraSevakJwtTokenUtil;
import com.agnyanam.yatrasevak.utils.YathraSevakUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/yatra")
public class YatraController {

	@Autowired
	private YathriRepository yatriRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private YathraSevakJwtTokenUtil jwtUtil;

	@GetMapping(path = "/ping")
	public String ping() {
		return "pong";
	}

	@PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public YathriSignUpResponse addYathri(@RequestBody @Valid YathriSignUpRequest yathriSignUpRequest) throws YathraSevakSignUpException {

		log.debug("Received a valid request for User Signup with name: " + yathriSignUpRequest.getUserName());

		//Checking whether the username is unique
		Boolean exists = Boolean.FALSE;
		try {

			exists = yatriRepo.findByUserName(yathriSignUpRequest.getUserName()) != null;
		} catch (Exception ex) {

			throw new YathraSevakSignUpException(YathraSevakConstants.INTERNAL_ERROR_CODE, YathraSevakConstants.INTERNAL_ERROR_MESSAGE);
		}

		if (exists) {

			throw new YathraSevakSignUpException(YathraSevakConstants.USERNAME_ALREADY_EXISTS_CODE,
					YathraSevakConstants.USERNAME_ALREADY_EXISTS_MESSAGE);
		}
		
		//Saving the user to DB if username is unique
		try {
			
			yathriSignUpRequest.setPassword(passwordEncoder.encode(yathriSignUpRequest.getPassword()));
			
			yatriRepo.save(YathraSevakUtils.ConvertSignUpRequestTToYathri(yathriSignUpRequest));

		} catch (Exception e) {

			throw new YathraSevakSignUpException(YathraSevakConstants.INTERNAL_ERROR_CODE, YathraSevakConstants.INTERNAL_ERROR_MESSAGE);
		}
		return new YathriSignUpResponse(YathraSevakConstants.YATHRA_USER_SIGNUP_SUCCESSFUL_CODE, YathraSevakConstants.YATHRA_USER_SIGNUP_SUCCESSFUL_MESSAGE);

	}
	
	@GetMapping(path = "/login" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
	public YathriLoginResponse loginCheck(@RequestBody YathriLoginRequest request ) throws YathraSevakLoginException {
		
		try {
			
			log.debug("Login Request received with Username " + request.getUserName());
			Yathri response = yatriRepo.findByUserName(request.getUserName());
			
			if(response != null) {
				
				if(passwordEncoder.matches(request.getPassword(), response.getPassword())) {
					
					return new YathriLoginResponse(YathraSevakConstants.YATHRI_LOGIN_SUCCESSFULL_CODE, YathraSevakConstants.YATHRI_LOGIN_SUCCESSFULL_MESSAGE , jwtUtil.generateToken(request.getUserName()));
				}
				else {
					return new YathriLoginResponse(YathraSevakConstants.YATHRI_LOGIN_INVALIDPASSWORD_CODE, YathraSevakConstants.YATHRI_LOGIN_INVALIDPASSWORD_MESSAGE);
				}
				
			}
			else {
				return new YathriLoginResponse(YathraSevakConstants.YATHRI_LOGIN_INVALIDUSER_CODE, YathraSevakConstants.YATHRI_LOGIN_INVALIDUSER_MESSAGE);
			}
			
		} catch (Exception e) {
			throw new YathraSevakLoginException(YathraSevakConstants.INTERNAL_ERROR_CODE , YathraSevakConstants.INTERNAL_ERROR_MESSAGE);
		}
	}

	@GetMapping(path = "/getYathriWithUserName")
	public GetYathriResponse getYathriWithUserName(@RequestParam("userName") String userName) throws GetYathriWithUsernameException {

		log.debug("Received a get request for yathri with username  :" + userName);
		Yathri response = null;
		try {
			log.debug("Invoking mongo db with the username");
			response = yatriRepo.findByUserName(userName);
			log.debug("User Name was found in the Data Base");
		} catch (Exception ex) {
			throw new GetYathriWithUsernameException(YathraSevakConstants.INTERNAL_ERROR_CODE, YathraSevakConstants.INTERNAL_ERROR_MESSAGE);
		}

		// Checking the response
		if (response != null) {
			return YathraSevakUtils.convertYathriResponseToGetYathri(response);
		} else {
			throw new GetYathriWithUsernameException(YathraSevakConstants.USERNAME_NOT_FOUND_CODE, YathraSevakConstants.USERNAME_NOT_FOUND_MESSAGE);
		}

	}
}
