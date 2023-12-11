package com.example.Final.Entity;

import javax.persistence.Entity;

@Entity
public class Admin extends Member{

	// Instance Variable 
	
	private String admin_number ;
	
	// Constructors 
	
	public Admin() {
		this("","","","","","","","","");
	}
	public Admin(String name , String family , String meli_code , String email , String phone ,
			String address , String admin_number , String gender , String password) {
		super(name,family,meli_code,email,phone,address,gender,password) ; 
		this.admin_number = admin_number ;
	}

	// Setter 
	
	public void setAdmin_number(String admin_number) {
		this.admin_number = admin_number;
	}
	
	// Getter
	
	public String getAdmin_number() {
		return admin_number;
	}
	
}
