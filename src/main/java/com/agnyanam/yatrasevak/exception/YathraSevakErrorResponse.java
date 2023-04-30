package com.agnyanam.yatrasevak.exception;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonRootName(value = "YathraSevakErrorResponse")
public class YathraSevakErrorResponse {
	
	String errorCode;
	String errorMessage;
}
