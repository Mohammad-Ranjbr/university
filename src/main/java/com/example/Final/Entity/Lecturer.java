package com.example.Final.Entity;

import javax.persistence.Entity;

@Entity
public class Lecturer extends Member{

	// Instance Variables
	
	private String lecturer_number ;

	// Constructors 
	
	public Lecturer() {
		this("","","","","","","","","");  
	}
	public Lecturer(String name , String family , String meli_code , String email , String phone ,
			String address , String lecturer_number , String gender , String password) {
		super(name,family,meli_code,email,phone,address,gender,password) ; 
		this.lecturer_number = lecturer_number ;
	}
	
	// Setters 
	
	public void setLecturer_number(String lecturer_number) {
		this.lecturer_number = lecturer_number;
	}
	
	// Getters 
	
	public String getLecturer_number() {
		return lecturer_number;
	}
	
}
