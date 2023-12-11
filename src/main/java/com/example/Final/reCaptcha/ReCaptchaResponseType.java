package com.example.Final.reCaptcha;

public class ReCaptchaResponseType {

	// Instance Variables
	
	private boolean success ;
	private String challenge_ts ;
	private String hostname ;
	
	// Setters 
	
	public void setsuccess(boolean success) {
		this.success = success ;
	}
	public void setChallenge_ts(String challenge_ts) {
		this.challenge_ts = challenge_ts ;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname ;
	}
	
	// Getters 
	
	public boolean isSuccess() {
		return success ;
	}
	public String getChallenge_ts() {
		return challenge_ts ;
	}
	public String getHostname() {
		return hostname ;
	}
	
}
