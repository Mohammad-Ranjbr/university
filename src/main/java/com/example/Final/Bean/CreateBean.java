package com.example.Final.Bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.Final.Entity.Admin;
import com.example.Final.Entity.Employee;
import com.example.Final.Entity.Lecturer;
import com.example.Final.Entity.Logs;
import com.example.Final.Entity.Student;
import com.example.Final.Service.PasswordValidator;
import com.example.Final.Service.PhoneValidator;

@Configuration
public class CreateBean {

	@Bean
	public Lecturer getLecturer() {
		return new Lecturer();
	}
	
	@Bean
	public Employee getEmployee() {
		return new Employee();
	}
	
	@Bean
	public Student getStudent() {
		return new Student();
	}
	
	@Bean
	public Admin getAdmin() {
		return new Admin();
	}
	
	@Bean
	public PasswordValidator getPasswordValidator() {
		return new PasswordValidator();
	}
	
	@Bean
	public PhoneValidator getPhoneValidator() {
		return new PhoneValidator();
	}
	
	@Bean
	public Logs getLogs() {
		return new Logs(); 
	}
	
}
