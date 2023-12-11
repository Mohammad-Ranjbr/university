package com.example.Final.Service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

@Service
public class PhoneValidator {

	public boolean validatePhoneNumber(String phone , String role , HttpSession httpSession) {
		String HTTPSESSION_ATTRIBUTE_NAME = "";
		
		if(role.equals("R_LECTURER")) {
			HTTPSESSION_ATTRIBUTE_NAME = "lError" ;
		} 
		else if(role.equals("R_EMPLOYEE")) {
			HTTPSESSION_ATTRIBUTE_NAME = "eError" ;
		}
		else if(role.equals("R_STUDENT")) {
			HTTPSESSION_ATTRIBUTE_NAME = "sError" ;
		}
		else if(role.equals("E_LECTURER")) {
			HTTPSESSION_ATTRIBUTE_NAME = "elError" ;
		}
		else if(role.equals("E_EMPLOYEE")) {
			HTTPSESSION_ATTRIBUTE_NAME = "eeError" ;
		}
		else if(role.equals("E_STUDENT")) {
			HTTPSESSION_ATTRIBUTE_NAME = "esError" ;
		}
		
		if(phone.trim().isEmpty()) { 
			httpSession.setAttribute(HTTPSESSION_ATTRIBUTE_NAME,"لطفا شماره موبایل را وارد کنید");
			return false;
		}
		else if(!phone.startsWith("0")) {
			httpSession.setAttribute(HTTPSESSION_ATTRIBUTE_NAME,"شماره موبایل باید با صفر شروع شود");
			return false;
		}
		else if(phone.length() != 11) {
			httpSession.setAttribute(HTTPSESSION_ATTRIBUTE_NAME,"شماره موبایل باید 11 رقم داشته باشد");
			return false;
		}
		return true ;
	}
	
}
