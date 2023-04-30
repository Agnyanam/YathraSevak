package com.agnyanam.yatrasevak.utils;

import com.agnyanam.yatrasevak.model.Yathri;
import com.agnyanam.yatrasevak.request.YathriSignUpRequest;
import com.agnyanam.yatrasevak.response.GetYathriResponse;

public class YathraSevakUtils {

	public static Yathri ConvertSignUpRequestTToYathri(YathriSignUpRequest yathriSignUpRequest) {

		Yathri yathri = new Yathri(yathriSignUpRequest.getUserName(), yathriSignUpRequest.getPassword(), yathriSignUpRequest.getFirstName(), yathriSignUpRequest.getGender(), yathriSignUpRequest.getDateOfBirth());
		yathri.setLastName(yathriSignUpRequest.getLastName());
		yathri.setAddress(yathriSignUpRequest.getAddress());
		yathri.setMobileNumber(yathriSignUpRequest.getMobileNumber());
		return yathri;
	}

	public static GetYathriResponse convertYathriResponseToGetYathri(Yathri yathri) {

		GetYathriResponse response = new GetYathriResponse();
		response.setUserName(yathri.getUserName());
		response.setMobileNumber(yathri.getMobileNumber());
		response.setAddress(yathri.getAddress());
		response.setDateOfBirth(yathri.getDateOfBirth());
		response.setGender(yathri.getGender());
		response.setFirstName(yathri.getLastName());
		response.setLastName(yathri.getLastName());
		response.setCode(YathraSevakConstants.YATHRI_GETUSER_SUCCESSFULL_CODE);
		response.setMessage(YathraSevakConstants.YATHRI_GETUSER_SUCCESSFULL_MESSAGE);
		return response;
	}

	
}
