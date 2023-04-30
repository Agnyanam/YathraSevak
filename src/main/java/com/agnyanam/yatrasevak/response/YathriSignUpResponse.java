package com.agnyanam.yatrasevak.response;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;

@JsonRootName("YathriSignUpResponse")
@Data
@AllArgsConstructor
public class YathriSignUpResponse {

	String code;
	String message;
}
