package com.example.Final.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Logs {

	// Instance variable
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)   
	private int id ;
	private String full_name ;
	private String email ;
	private String role ;
	@Temporal(TemporalType.TIMESTAMP) 
	private Date login_time ;
	@Temporal(TemporalType.TIMESTAMP)
	private Date logout_time ;
	
	// Constructors 
	
	public Logs() {
		this("","","",null,null);
	}
	public Logs(String full_name , String email , String role , Date login_time , Date logout_time) {
		this.full_name = full_name ;
		this.email = email ;
		this.role = role ;
		this.login_time = login_time ;
		this.logout_time = logout_time ;
	}
	
	// Setters 
	
	public Logs setId(int id) {
		this.id = id;
		return this ;
	}
	public Logs setFull_name(String full_name) {
		this.full_name = full_name;
		return this ;
	}
	public Logs setEmail(String email) {
		this.email = email;
		return this ;
	}
	public Logs setRole(String role) {
		this.role = role;
		return this ;
	}
	public Logs setLogin_time(Date login_time) {
		this.login_time = login_time;
		return this ;
	}
	public Logs setLogout_time(Date logout_time) {
		this.logout_time = logout_time;
		return this ;
	}
	
	// Getters 
	
	public int getId() {
		return id;
	}
	public String getFull_name() {
		return full_name;
	}
	public String getEmail() {
		return email;
	}
	public String getRole() {
		return role;
	}
	public Date getLogin_time() {
		return login_time;
	}
	public Date getLogout_time() {
		return logout_time;
	} 

}
