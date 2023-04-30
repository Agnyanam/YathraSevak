package com.agnyanam.yatrasevak.model;

import java.math.BigInteger;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import lombok.Data;

@Data
@Document(collection = "Yathri")
public class Yathri {

	@Id
	private String id;
	
	@NonNull
	private String userName;
	
	@NonNull
	private String password;
	
	@NonNull
	private String firstName;
	
	@Nullable
	private String lastName;
	
	@Nullable
	private BigInteger mobileNumber;
	
	@Nullable
	private String address;
	
	@NonNull
	private String gender;
	
	@NonNull
	private String dateOfBirth;
	
}
