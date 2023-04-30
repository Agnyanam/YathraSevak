package com.agnyanam.yatrasevak.response;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName("GetYathriResponse")
@JsonInclude(Include.NON_NULL)
public class GetYathriResponse {

	private String code;
	
	private String message;
	
	private String userName;
	
	private String firstName;
	
	private String lastName;
	
	private BigInteger mobileNumber;
	
	private String address;
	
	private String gender;
	
	private String dateOfBirth;
}
