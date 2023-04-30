package com.agnyanam.yatrasevak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.agnyanam.yatrasevak.response.GetYathriResponse;
import com.agnyanam.yatrasevak.response.YathriSignUpResponse;
import com.agnyanam.yatrasevak.utils.YathraSevakConstants;

@ControllerAdvice
public class YathriExceptionController {

	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(YathraSevakSignUpException.class)
	@ResponseBody
	public YathriSignUpResponse yathraSevakSignUpException(YathraSevakSignUpException ex) {
		return new YathriSignUpResponse(ex.getCode(), ex.getMessage());
	}
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public YathriSignUpResponse argumentsInvalidException(MethodArgumentNotValidException ex) {
		return new YathriSignUpResponse(YathraSevakConstants.YATHRA_DETAILS_VALIDATIONS_CODE , ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}
	
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(YathraSevakException.class)
	@ResponseBody
	public YathraSevakErrorResponse yathraSevakException(YathraSevakException ex) {
		return new YathraSevakErrorResponse(ex.getCode(), ex.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(GetYathriWithUsernameException.class)
	@ResponseBody
	public GetYathriResponse  getYathriError(GetYathriWithUsernameException ex) {
		GetYathriResponse response = new GetYathriResponse();
		response.setCode(ex.getCode());
		response.setMessage(ex.getMessage());
		return response;
	}
	
}
 