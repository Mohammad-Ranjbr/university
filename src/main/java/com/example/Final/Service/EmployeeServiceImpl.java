package com.example.Final.Service;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Final.Entity.Employee;
import com.example.Final.Repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository ;
	@Autowired
	private PasswordValidator passwordValidator ;
	@Autowired
	private PhoneValidator phoneValidator ;
	
	@Override
	public void addEmployee(Employee employee) {
		employeeRepository.save(employee); 
	}

	@Override
	public boolean existsEmployeeByEmail(String email) {
		return employeeRepository.existsByEmail(email);
	}

	@Override
	public boolean existsEmployeeByPhone(String phone) {
		return employeeRepository.existsByPhone(phone); 
	}

	@Override
	public Employee getEmployeeByEmail(String email) {
		return employeeRepository.getByEmail(email);
	}

	@Override
	public void deleteEmployeeById(int id) {
		employeeRepository.deleteById(id); 
	}

	@Override
	public boolean validateEmployeeRegisterData(HttpSession httpSession, Employee employee) {
		if(employee.getName().trim().isEmpty()) {
			httpSession.setAttribute("eError","لطفا نام را وارد کنید");
			return false;
		}
		else if(employee.getFamily().trim().isEmpty()) {
			httpSession.setAttribute("eError","لطفا نام خانوادگی را وارد کنید");
			return false;
		}
		else if(employee.getMeli_code().trim().isEmpty()) {
			httpSession.setAttribute("eError","لطفا کد ملی را وارد کنید");
			return false;
		}
		else if(employee.getEmail().trim().isEmpty()) {
			httpSession.setAttribute("eError","لطفا ایمیل را وارد کنید");
			return false;
		}
		else if(!employee.getEmail().matches("^.+@.+\\..+$")) {
			httpSession.setAttribute("eError","لطفا '@' را در آدرس ایمیل وارد کنید");
			return false;
		}
		else if(employeeRepository.existsByEmail(employee.getEmail())) {
			httpSession.setAttribute("eError","این ایمیل از قبل وجود دارد");
			return false;
		}
		else if(!phoneValidator.validatePhoneNumber(employee.getPhone(),"R_EMPLOYEE",httpSession)) {
			return false ;
		}
		else if(employeeRepository.existsByPhone(employee.getPhone())) { 
			httpSession.setAttribute("eError","این شماره از قبل وجود دارد"); 
			return false;
		}
		else if(employee.getEmployee_number().trim().isEmpty()) { 
			httpSession.setAttribute("eError","لطفا کد پرسنلی را وارد کنید");
			return false;
		}
		else if(employee.getAddress().trim().isEmpty()) {
			httpSession.setAttribute("eError","لطفا ادرس را وارد کنید");
			return false;
		}
		else if(!passwordValidator.validatePassword(employee.getPassword(),"R_EMPLOYEE",httpSession)) {
			return false ; 
		}
		else if(employee.getGender().trim().isEmpty()) {
			httpSession.setAttribute("eError","لطفا جنسیت را انتخاب کنید"); 
			return false;
		}
		return true ;
	}

	@Override
	public boolean validateEmployeeLoginData(HttpSession httpSession, Employee employee) {
		if(employee.getEmail().trim().isEmpty()) {
			httpSession.setAttribute("elError","لطفا ایمیل را وارد کنید");
			return false;
		}
		else if(!employee.getEmail().matches("^.+@.+\\..+$")) {
			httpSession.setAttribute("elError","لطفا '@' را در آدرس ایمیل وارد کنید");
			return false ;
		}
		else if(employee.getPassword().trim().isEmpty()) {
			httpSession.setAttribute("elError","لطفا رمز عبور را وارد کنید");
			return false;
		}
		return true ;
	}

	@Override
	public boolean validateEmployeeUpdateData(HttpSession httpSession, Employee employee) {
		if(employee.getName().trim().isEmpty()) {
			httpSession.setAttribute("eeError","لطفا نام را وارد کنید");
			return false;
		}
		else if(employee.getFamily().trim().isEmpty()) {
			httpSession.setAttribute("eeError","لطفا نام خانوادگی را وارد کنید");
			return false;
		}
		else if(employee.getMeli_code().trim().isEmpty()) {
			httpSession.setAttribute("eeError","لطفا کد ملی را وارد کنید");
			return false;
		}
		else if(employee.getEmail().trim().isEmpty()) {
			httpSession.setAttribute("eeError","لطفا ایمیل را وارد کنید");
			return false;
		}
		if(!employee.getEmail().matches("^.+@.+\\..+$")) {
			httpSession.setAttribute("eeError","لطفا '@' را در آدرس ایمیل وارد کنید");
			return false;
		}
		else if(!phoneValidator.validatePhoneNumber(employee.getPhone(),"E_EMPLOYEE",httpSession) ) {
			return false ; 
		}
		else if (employeeRepository.existsByPhone(employee.getPhone()) && !getEmployeeById(employee.getId()).getPhone().equals(employee.getPhone())) {
			httpSession.setAttribute("eeError","این شماره از قبل وجود دارد");
			return false;
		}
		else if(employee.getEmployee_number().trim().isEmpty()) { 
			httpSession.setAttribute("eeError","لطفا کد پرسنلی را وارد کنید");
			return false;
		}
		else if(employee.getAddress().trim().isEmpty()) {
			httpSession.setAttribute("eeError","لطفا ادرس را وارد کنید");
			return false;
		}
		else if(!passwordValidator.validatePassword(employee.getPassword(),"E_EMPLOYEE",httpSession) && 
				!getEmployeeById(employee.getId()).getPassword().equals(employee.getPassword())) {
			return false ; 
		}
		else if(employee.getGender().trim().isEmpty()) {
			httpSession.setAttribute("eeError","لطفا جنسیت را انتخاب کنید"); 
			return false;
		}
		return true ;
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll(); 
	}

	@Override
	public Employee getEmployeeById(int id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if(employee.isPresent()) {
			return employee.get();
		}
		return null ;
	}

	@Override
	public boolean validateEmployeeForgetPasswordData(HttpSession httpSession, Employee employee) {
		if(employee.getEmail().trim().isEmpty()) {
			httpSession.setAttribute("feError","لطفا ایمیل را وارد کنید");
			return false;
		}
		else if(!employee.getEmail().matches("^.+@.+\\..+$")) {
			httpSession.setAttribute("feError","لطفا '@' را در آدرس ایمیل وارد کنید");
			return false ;
		}
		return true ;
	}

	@Override
	public boolean CheckingEmployeeDataChanged(HttpSession httpSession, Employee employee) {
		if(getEmployeeById(employee.getId()).getName().equals(employee.getName()) &&
				getEmployeeById(employee.getId()).getFamily().equals(employee.getFamily()) &&
				getEmployeeById(employee.getId()).getMeli_code().equals(employee.getMeli_code()) &&
				getEmployeeById(employee.getId()).getEmail().equals(employee.getEmail()) &&
				getEmployeeById(employee.getId()).getPhone().equals(employee.getPhone()) &&
				getEmployeeById(employee.getId()).getAddress().equals(employee.getAddress()) &&
				getEmployeeById(employee.getId()).getGender().equals(employee.getGender()) &&
				getEmployeeById(employee.getId()).getPassword().equals(employee.getPassword()) &&
				getEmployeeById(employee.getId()).getEmployee_number().equals(employee.getEmployee_number())) {
			httpSession.setAttribute("ceeMessage","تغییری در اطلاعات رخ نداده است");
			return true ;
		}
		return false ;
	}

}
