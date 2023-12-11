package com.example.Final.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String showIndext1() {
		return "/main/index"; 
	}
	
	@GetMapping("/index")
	public String showIndex2() {
		return "/main/index"; 
	}

	@GetMapping("/lhome")
	public String showLecturerHome() {
		return "/lecturer/l-home";  
	}
	
	@GetMapping("/ehome")
	public String showEmployeeHome() {
		return "/employee/e-home"; 
	}
	
	@GetMapping("/shome")
	public String showStudentHome() {
		return "/student/s-home"; 
	}
	
	@GetMapping("/a-home")
	public String showAdminHomePage() {
		return "/admin/a-home"; 
	}
	
}
