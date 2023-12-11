package com.example.Final.Service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

@Service
public class PasswordValidator {

	private final static String DIGIT_PATTERN = "\\D+" ;
	private final static String CHARACTER_PATTERN = "(?i)[^a-z]+" ;
	private final static String SPECIALCHARACTER_PATTERN = "[^!@#$%^&*()_+.-]" ;
	
	public boolean validatePassword(String password , String role , HttpSession httpSession) {
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
		else if(role.equals("FP_LECTURER")) {
			HTTPSESSION_ATTRIBUTE_NAME = "flError" ;
		}
		else if(role.equals("FP_EMPLOYEE")) {
			HTTPSESSION_ATTRIBUTE_NAME = "feError" ;
		}
		else if(role.equals("FP_STUDENT")) {
			HTTPSESSION_ATTRIBUTE_NAME = "fsError" ;
		}
		
		if(password.trim().isEmpty()) {
			httpSession.setAttribute(HTTPSESSION_ATTRIBUTE_NAME,"لطفا رمز عبور را وارد کنید");
			return false;
		}
		else if(password.length() < 4) {
			httpSession.setAttribute(HTTPSESSION_ATTRIBUTE_NAME,"• رمز عبور باید حداقل 4 کاراکتر داشته باشد"); 
			return false;
		}
		else if(password.replaceAll(DIGIT_PATTERN, "").length() < 1) { 
			httpSession.setAttribute(HTTPSESSION_ATTRIBUTE_NAME,"• رمز عبور باید حداقل دارای یک رقم [0-9] باشد.");
			return false; 
		}
		else if(password.replaceAll(CHARACTER_PATTERN, "").length() < 1) {
			httpSession.setAttribute(HTTPSESSION_ATTRIBUTE_NAME,"• رمز عبور باید حداقل یک حرف لاتین بزرگ یا کوچک داشته باشد."); 
			return false; 
		}
		else if(password.replaceAll(SPECIALCHARACTER_PATTERN, "").length() < 1) { 
			httpSession.setAttribute(HTTPSESSION_ATTRIBUTE_NAME,"• رمز عبور باید حداقل یک کاراکتر خاص مانند ! @ # و ( ).");
			return false;
		}
		else if(password.length() > 20) {
			httpSession.setAttribute(HTTPSESSION_ATTRIBUTE_NAME,"• رمز عبور باید حداکثر 20 کاراکتر داشته باشد");
			return false;
		}
		return true ;
	}
	
}
