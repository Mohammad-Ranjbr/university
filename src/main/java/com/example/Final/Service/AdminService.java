package com.example.Final.Service;

import javax.servlet.http.HttpSession;
import com.example.Final.Entity.Admin;

public interface AdminService {

	public boolean existsAdminByEmail(String email);
	public boolean validateAdminLoginData(HttpSession httpSession , Admin admin); 
	public Admin getAdminByEmail(String email); 
	
}
