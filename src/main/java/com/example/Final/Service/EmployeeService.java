package com.example.Final.Service;

import java.util.List;

import javax.servlet.http.HttpSession;
import com.example.Final.Entity.Employee;

public interface EmployeeService {

	public void addEmployee(Employee employee); 
	public boolean existsEmployeeByEmail(String email);
	public boolean existsEmployeeByPhone(String phone);
	public Employee getEmployeeByEmail(String email); 
	public void deleteEmployeeById(int id);
	public boolean validateEmployeeRegisterData(HttpSession httpSession , Employee employee);
	public boolean validateEmployeeLoginData(HttpSession httpSession , Employee employee);
	public boolean validateEmployeeUpdateData(HttpSession httpSession , Employee employee); 
	public boolean validateEmployeeForgetPasswordData(HttpSession httpSession , Employee employee);
	public boolean CheckingEmployeeDataChanged(HttpSession httpSession , Employee employee);
	public List<Employee> getAllEmployees();
	public Employee getEmployeeById(int id); 
	
}
