package com.example.Final.Service;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Final.Entity.Admin;
import com.example.Final.Repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {
 
	@Autowired
	private AdminRepository adminRepository ;
	
	public boolean existsAdminByEmail(String email) {
		return adminRepository.existsByEmail(email);
	}
	
	@Override
	public boolean validateAdminLoginData(HttpSession httpSession, Admin admin) {
		if(admin.getEmail().trim().isEmpty()) {
			httpSession.setAttribute("alError","لطفا ایمیل را وارد کنید");
			return false ;
		}
		else if(!admin.getEmail().matches("^.+@.+\\..+$")) {
			httpSession.setAttribute("alError","لطفا '@' را در آدرس ایمیل وارد کنید");
			return false ;
		}
		else if(admin.getPassword().trim().isEmpty()) {
			httpSession.setAttribute("alError","لطفا رمز عبور را وارد کنید"); 
			return false ;
		}
		return true ;
	}

	@Override
	public Admin getAdminByEmail(String email) {
		return adminRepository.getByEmail(email); 
	}
	
}
