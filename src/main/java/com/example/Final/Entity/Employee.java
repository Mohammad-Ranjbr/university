package com.example.Final.Entity;

import javax.persistence.Entity;

@Entity
public class Employee extends Member{

	// Instance Variable
	
	public String employee_number ;

	// Constructors 
	
	public Employee() {
		this("","","","","","","","","");
	}
	public Employee(String name , String family , String meli_code , String email , String phone , String address 
			, String employee_number , String gender , String password) { 
		super(name,family,meli_code,email,phone,address,gender,password) ;
		this.employee_number = employee_number ;
	}
	
	// Setters 
	
	public void setEmployee_number(String employee_number) {
		this.employee_number = employee_number;
	}
	
	// Getters 
	
	public String getEmployee_number() {
		return employee_number;
	}
		
}
