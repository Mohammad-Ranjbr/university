package com.example.Final.Service;

import java.util.List;
import javax.servlet.http.HttpSession;
import com.example.Final.Entity.Lecturer;

public interface LecturerService {

	public void addLecturer(Lecturer lecturer);
	public boolean existsLecturerByEmail(String email); 
	public boolean existsLecturerByPhone(String phone);
	public Lecturer getLecturerByEmail(String email);
	public void deleteLecturerById(int id);
	public boolean validateLecturerRegisterData(HttpSession httpSession , Lecturer lecturer);
	public boolean validateLecturerLoginData(HttpSession httpSession , Lecturer lecturer);
	public boolean validateLecturerUpdateData(HttpSession httpSession , Lecturer lecturer);
	public boolean validateLecturerForgetPasswordData(HttpSession httpSession , Lecturer lecturer);
	public boolean CheckingLecturerDataChanged(HttpSession httpSession , Lecturer lecturer);
	public List<Lecturer> getAllLecturers(); 
	public Lecturer getLecturerById(int id);
	
}
