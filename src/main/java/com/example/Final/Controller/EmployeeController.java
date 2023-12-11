package com.example.Final.Controller;

import java.util.Date;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Final.Entity.Employee;
import com.example.Final.Entity.Logs;
import com.example.Final.Service.EmployeeServiceImpl;
import com.example.Final.Service.LogsServiceImpl;
import com.example.Final.Service.PasswordValidator;
import com.example.Final.Service.ReCaptchaValidationService;

@Controller
public class EmployeeController {

	// instance Variable 
	
	@Autowired
	private EmployeeServiceImpl employeeServiceImpl ;
	@Autowired
	private Employee registerEmployee ;
	@Autowired
	private Employee loginEmployee ;
	@Autowired
	private Employee editEmployee ;
	@Autowired
	private Employee deleteEmployee ;
	@Autowired
	private Employee forgetPasswordEmployee ;
	@Autowired
	private PasswordValidator passwordValidator ;
	@Autowired
	private LogsServiceImpl logsServiceImpl ;
	@Autowired
	private Logs logs ;
	@Autowired
	private ReCaptchaValidationService validator;
	
	// Setters 
	
	private void setLog(Logs log) {
		this.logs = log ;
	}
	
	// Getters 
	
	private Logs getLog() {
		return logs ;
	} 
	
	@GetMapping("/e-register")
	public String showEmployeeRegisterPage(Model model) {
		model.addAttribute("employee",registerEmployee); 
		return "/employee/e-register";
	}
	
	@GetMapping("/e-home")
	public String showEmployeeHome() {
		return "/employee/e-home"; 
	}
	
	@GetMapping("/e-login")
	public String showEployeeLoginPage(Model model) {
		model.addAttribute("employee",loginEmployee);
		return "/employee/e-login"; 
	}
	
	@GetMapping("/e-profile")
	public String showEmployeeProfilePage() {
		return "/employee/e-profile"; 
	}

	@GetMapping("/e-edit")
	public String showEmployeeEditPage(HttpSession httpSession , Model model) {
		if(employeeServiceImpl.existsEmployeeByEmail(String.valueOf(httpSession.getAttribute("employeeEmail")))) {
			editEmployee = employeeServiceImpl.getEmployeeByEmail(String.valueOf(httpSession.getAttribute("employeeEmail")));
			model.addAttribute("employee",editEmployee);
			return "/employee/e-edit";
		}
		return "redirect:/e-profile";  
	}
	
	@PostMapping("/eregister")
	public String submitEmployeeRegister(@ModelAttribute Employee employee,HttpSession httpSession
			, @RequestParam(name="g-recaptcha-response") String captcha) {
		if(!employeeServiceImpl.validateEmployeeRegisterData(httpSession, employee)) {
			return "employee/e-register"; 
		}
		else {
			if(validator.validateCaptcha(captcha)) { 
				employeeServiceImpl.addEmployee(employee);
				httpSession.setAttribute("employeeEmail",employee.getEmail()); 
				httpSession.setMaxInactiveInterval(24 * 60 * 60); 
				httpSession.setAttribute("eMessage","کارمند با موفقیت ثبت شد");
				return "redirect:/e-login";
			}
			else {
				httpSession.setAttribute("eError","لطفا گزینه ی من ربات نیستم را انتخاب کنید"); 
				return "employee/e-register";
			}
		}
	}
	
	@PostMapping("/elogin")
	public String submitEmployeeLogin(@ModelAttribute Employee employee , HttpSession httpSession
			, @RequestParam(name="g-recaptcha-response") String captcha){
		Logs log = new Logs();
		if(!employeeServiceImpl.validateEmployeeLoginData(httpSession, employee)) { 
			return "employee/e-login";
		}
		else {
			if(validator.validateCaptcha(captcha)) {
				if(employeeServiceImpl.existsEmployeeByEmail(employee.getEmail())) {
					if(employeeServiceImpl.getEmployeeByEmail(employee.getEmail()).getPassword().equals(employee.getPassword())) {
						httpSession.setAttribute("employeeEmail",employee.getEmail()); 
						logsServiceImpl.addLog(log 
								.setFull_name(employeeServiceImpl.getEmployeeByEmail(employee.getEmail()).getName()+" "+
										employeeServiceImpl.getEmployeeByEmail(employee.getEmail()).getFamily()) 
								.setEmail(employee.getEmail())
								.setRole("Employee")
								.setLogin_time(new Date()));  
						setLog(log);
						return "redirect:/e-profile"; 
					}
					else {
						httpSession.setAttribute("elError","رمز عبور اشتباه است");
					}
				}
				else {
					httpSession.setAttribute("elError","کارمندی با این ایمیل وجود ندارد");
				}
				return "employee/e-login";
		}
		else {
			httpSession.setAttribute("elError","لطفا گزینه ی من ربات نیستم را انتخاب کنید");  
			return "employee/e-login";
		}
		}
	}
	
	@PostMapping("/eedit")
	public String submitEmployeeEdit(@ModelAttribute Employee employee , HttpSession httpSession) {
		if(!employeeServiceImpl.validateEmployeeUpdateData(httpSession, employee)) {
			return "/employee/e-edit"; 
		}
		else if(employeeServiceImpl.CheckingEmployeeDataChanged(httpSession, employee)) { 
			return "redirect:/e-profile" ;
		}
		else {
			if(httpSession.getAttribute("employeeEmail").equals(employee.getEmail())) {
				this.editEmployee = employeeServiceImpl.getEmployeeByEmail(String.valueOf(httpSession.getAttribute("employeeEmail")));   
				employee.setId(this.editEmployee.getId()); 
				employeeServiceImpl.addEmployee(employee);
				httpSession.setAttribute("eeMessage","اطلاعات کارمند با موفقیت ویرایش شد");
			}
			else {
				if(employeeServiceImpl.existsEmployeeByEmail(employee.getEmail())) {
					httpSession.setAttribute("eeError","کارمندی با این ایمیل وجود دارد"); 
					return "/employee/e-edit";
				} 
				else {
					this.editEmployee = employeeServiceImpl.getEmployeeByEmail(String.valueOf(httpSession.getAttribute("employeeEmail")));
					employeeServiceImpl.deleteEmployeeById(this.editEmployee.getId());
					employeeServiceImpl.addEmployee(employee);
					httpSession.setAttribute("employeeEmail",employee.getEmail());
					httpSession.setAttribute("eeMessage","اطلاعات کارمند با موفقیت ویرایش شد");
				}
			}
			return "redirect:/e-profile" ;
		}
	}
	
	@PostMapping("/elogout")
	public String submitEmployeeLogout(HttpSession httpSession) {
		logsServiceImpl.addLog(getLog() 
				.setLogout_time(new Date())); 
		httpSession.removeAttribute("employeeEmail");
		return "redirect:/e-home"; 
	}
	
	@PostMapping("/edelete")
	public String submitEmployeeDeleteAccount(HttpSession httpSession) {
		deleteEmployee = employeeServiceImpl.getEmployeeByEmail(String.valueOf(httpSession.getAttribute("employeeEmail")));
		employeeServiceImpl.deleteEmployeeById(deleteEmployee.getId());
		httpSession.removeAttribute("employeeEmail");
		httpSession.setAttribute("eDeleteMessage","حساب کاربری با موفقیت حذف شد");
		return "redirect:/e-home";
	}
	
	@GetMapping("/e-forget")
	public String showEmployeeForgetPasswordPage(Model model) {
		model.addAttribute("employee",forgetPasswordEmployee);
		return "/employee/e-forgetPassword";
	}
	
	@PostMapping("/eforget")
	public String submitLecturerForgetPassword(@ModelAttribute Employee employee , HttpSession httpSession) {
		if(!employeeServiceImpl.validateEmployeeForgetPasswordData(httpSession, employee)) {
			return "/employee/e-forgetPassword"; 
		}
		else {
			if(employeeServiceImpl.existsEmployeeByEmail(employee.getEmail())){
				if(!passwordValidator.validatePassword(employee.getPassword(),"FP_EMPLOYEE",httpSession)){
					return "/employee/e-forgetPassword";
				}
				else {
					if(employee.getPassword().equals(employee.getName())) {
						forgetPasswordEmployee = employeeServiceImpl.getEmployeeByEmail(employee.getEmail());
						forgetPasswordEmployee.setPassword(employee.getPassword());
						employeeServiceImpl.addEmployee(forgetPasswordEmployee);
						System.out.println(forgetPasswordEmployee.getId()); 
						httpSession.setAttribute("eMessage","رمز عبور کارمند با موفقیت تغییر یافت"); 
						return "redirect:/e-login";
					}
					else {
						httpSession.setAttribute("feError","تکرار رمز عبور اشتباه است"); 
						return "/employee/e-forgetPassword";
					} 
				}
			}
			else {
				httpSession.setAttribute("feError","کارمندی با این ایمیل وجود ندارد"); 
				return "/employee/e-forgetPassword";
			}
		}
	}
	
}
