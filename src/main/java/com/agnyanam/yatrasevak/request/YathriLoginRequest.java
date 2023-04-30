package com.agnyanam.yatrasevak.request;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName(value = "YathriLoginRequest")
public class YathriLoginRequest {

	String userName;
	String password;
}
