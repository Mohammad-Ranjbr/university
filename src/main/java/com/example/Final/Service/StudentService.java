package com.example.Final.Service;

import java.util.List;
import javax.servlet.http.HttpSession;
import com.example.Final.Entity.Student;

public interface StudentService {

	public void addStudent(Student student);
	public boolean existsStudentByEmail(String email);
	public boolean existsStudentByPhone(String phone);
	public Student getStudentByEmail(String email);
	public void deleteStudentById(int id);
	public boolean validateStudentRegisterData(HttpSession httpSession , Student student);
	public boolean validateStudentLoginData(HttpSession httpSession , Student student);
	public boolean validateStudentUpdateData(HttpSession httpSession , Student student); 
	public boolean validateStudentForgetPasswordData(HttpSession httpSession , Student student); 
	public boolean CheckingStudentDataChanged(HttpSession httpSession , Student student);
	public List<Student> getAllStudents();
	public Student getStudentById(int id);
	
}
