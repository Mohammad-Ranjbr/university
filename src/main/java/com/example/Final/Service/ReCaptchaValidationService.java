package com.example.Final.Service;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.example.Final.reCaptcha.ReCaptchaResponseType;

@Service
public class ReCaptchaValidationService {

	private static final String GOOGLE_RECAPTCHA_ENDPOINT = "https://www.google.com/recaptcha/api/siteverify";
	private final String RECAPTCHA_SECRET = "6LeRJPUkAAAAABgcyzKtSd2gNKgwsanpBMzRLYIQ";
	
	 public boolean validateCaptcha(String captchaResponse){
	        RestTemplate restTemplate = new RestTemplate();

	        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
	        requestMap.add("secret", RECAPTCHA_SECRET);
	        requestMap.add("response", captchaResponse);

	        ReCaptchaResponseType apiResponse = restTemplate.postForObject(GOOGLE_RECAPTCHA_ENDPOINT, requestMap, ReCaptchaResponseType.class);
	        if(apiResponse == null){
	            return false;
	        }

	        return Boolean.TRUE.equals(apiResponse.isSuccess());
	    }
	 
}
