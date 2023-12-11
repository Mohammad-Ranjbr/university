package com.example.Final.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Member {

	// Instance variable
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String name ;
	private String family;
	private String meli_code ;
	private String email ;
	private String phone ;
	private String address ;
	private String gender ;
	private String password ;
	
	// Constructor
	
	public Member(){
		this("","","","","","","","");
	}
	public Member(String name , String family , String meli_code , String email , String phone , String address , String gender , String password ){ 
		this.name = name ;
		this.family = family ;
		this.meli_code = meli_code ;
		this.email = email ;
		this.phone = phone ;
		this.address = address ;
		this.gender = gender ;
		this.password = password ;
	}
	
	// Setters
	
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name){
		this.name = name ;
	}
	public void setFamily(String family){
		this.family = family ;
	}
	public void setMeli_code(String meli_code){
		this.meli_code = meli_code ;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	// Getters 
	
	public int getId() {
		return id;
	}
	public String getName(){
		return name ;
	}
	public String getFamily(){
		return family ;
	}
	public String getMeli_code(){
		return meli_code ;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public String getAddress() {
		return address;
	}
	public String getGender() {
		return gender;
	}
	public String getPassword() {
		return password;
	}
		
}
