package com.agnyanam.yatrasevak.request;

import java.math.BigInteger;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonRootName(value = "YathriSignUpRequest")
public class YathriSignUpRequest {
    
	@NotBlank(message ="The username should not be null or empty")
	@NotNull(message ="The username should not be null or empty")
	@Size(min=4, max=10 , message= "The size of username should be between 4 and 10")
	private String userName;
	
	@NotBlank(message ="The password should not be null or empty")
	@NotNull(message ="The password should not be null or empty")
	@Size(min=8, max=50 , message= "The size of password should be more than 8")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$" , message = "Password supplied is not in specified format")
	private String password;
	
	@NotBlank(message ="The Firstname should not be null or empty")
	@NotNull(message ="The Firstname should not be null or empty")
	private String firstName;
	
	private String lastName;
	
	private BigInteger mobileNumber;
	
	private String address;
	
	@NotBlank(message ="The gender should not be null or empty")
	@NotNull(message ="The Gender should not be null or empty")
	private String gender;
	
	@NotBlank(message ="The DOB should not be null or empty")
	@NotNull(message ="The DOB should not be null or empty")
	private String dateOfBirth;
	
}
