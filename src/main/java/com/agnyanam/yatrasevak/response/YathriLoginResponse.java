package com.agnyanam.yatrasevak.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonRootName(value = "YathriLoginResponse")
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
public class YathriLoginResponse {
	String code ; 
	String message;
	String token;
	
	public YathriLoginResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	
}
