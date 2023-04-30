package com.agnyanam.yatrasevak.utils;

public class YathraSevakConstants {

	//Internal server error
	public static String INTERNAL_ERROR_CODE = "01";
	public static String INTERNAL_ERROR_MESSAGE = "Internal Server Error";
	
	//Username not found
	public static String USERNAME_NOT_FOUND_CODE = "02";
	public static String USERNAME_NOT_FOUND_MESSAGE = "UserName not found in our records";
	
	//Username already exists
	public static String USERNAME_ALREADY_EXISTS_CODE = "03";
	public static String USERNAME_ALREADY_EXISTS_MESSAGE = "Username already present in the records, Kindly try with another username";
	
	//Input validations error
	public static String YATHRA_DETAILS_VALIDATIONS_CODE = "04";
	
	//User Signup 
	public static String YATHRA_USER_SIGNUP_SUCCESSFUL_CODE = "05";
	public static String YATHRA_USER_SIGNUP_SUCCESSFUL_MESSAGE = "User Added Successfully";
	
	//Get yathri
	public static String YATHRI_GETUSER_SUCCESSFULL_CODE="06";
	public static String YATHRI_GETUSER_SUCCESSFULL_MESSAGE = "User Retrieved Successfully";
	
	//login 
	public static String YATHRI_LOGIN_SUCCESSFULL_CODE="07";
	public static String YATHRI_LOGIN_SUCCESSFULL_MESSAGE = "User Logged Successfully";
	
	public static String YATHRI_LOGIN_INVALIDUSER_CODE="08";
	public static String YATHRI_LOGIN_INVALIDUSER_MESSAGE = "User Name is invalid. Kindly check the username and retry";
	
	public static String YATHRI_LOGIN_INVALIDPASSWORD_CODE="09";
	public static String YATHRI_LOGIN_INVALIDPASSWORD_MESSAGE = "Password is invalid. Kindly check the password and retry";
	
	
	public static String YATHRI_AUTHENTICATION_ERROR_CODE = "10";
	public static String YATHRI_AUTHENTICATION_ERROR_INVALIDJWT = "Invalid JWT Token supplied";
	public static String YATHRI_AUTHENTICATION_ERROR_AUTHORIZATION_HEADER_NOT_SUPPLIED = "Authorization Header not supplied or Token did not start with Bearer";
	public static String YATHRI_AUTHENTICATION_ERROR_JWTEXPIRED = "The supplied JWT Token is expired";
	public static String YATHRI_AUTHENTICATION_ERROR_BEARERSTRING_NOT_SUPPLIED = "JWT Token needs to start with Bearer";
	
	
}

