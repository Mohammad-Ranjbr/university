package com.example.Final.Entity;

import javax.persistence.Entity;

@Entity
public class Student extends Member{

	// Instance Variable 
	
	private String student_number ;
	private String start_date ;
	
	// Constructors
	
	public Student() {
		this("","","","","","","","","","");
	}
	public Student(String name , String family , String meli_code , String email , String phone , String address , 
			String student_number , String start_date , String gender , String password) {
		super(name,family,meli_code,email,phone,address,gender,password) ;
		this.student_number = student_number ;
		this.start_date = start_date ;
	}
	
	// Setters 
	
	public void setStudent_number(String student_number) {
		this.student_number = student_number;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	// Getters 
	
	public String getStart_date() {
		return start_date;
	}
	public String getStudent_number() {
		return student_number;
	}
		
}
