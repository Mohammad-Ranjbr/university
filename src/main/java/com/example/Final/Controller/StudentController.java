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

import com.example.Final.Entity.Logs;
import com.example.Final.Entity.Student;
import com.example.Final.Service.LogsServiceImpl;
import com.example.Final.Service.PasswordValidator;
import com.example.Final.Service.ReCaptchaValidationService;
import com.example.Final.Service.StudentServiceImpl;

@Controller
public class StudentController {

	// Instance Variables 
	
	@Autowired
	private StudentServiceImpl studentServiceImpl ;
	@Autowired
	private Student registerStudent ;
	@Autowired
	private Student loginStudent ;
	@Autowired
	private Student editStudent ;
	@Autowired
	private Student deleteStudent ;
	@Autowired
	private Student forgetPasswordStudent ;
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
	
	@GetMapping("/s-register")
	public String showStudentRegisterPage(Model model) {
		model.addAttribute("student",registerStudent); 
		return "/student/s-register";  
	}
	
	@GetMapping("/s-home")
	public String showStudentHome() {
		return "/student/s-home";
	}
	
	@GetMapping("/s-login")
	public String showStudentLoginPage(Model model) {
		model.addAttribute("student",loginStudent); 
		return "/student/s-login";
	}
	
	@GetMapping("/s-profile")
	public String showStudentProfilePage() {
		return "/student/s-profile"; 
	}
	
	@GetMapping("/s-edit")
	public String showstudentEditPage(HttpSession httpSession , Model model) {
		if(studentServiceImpl.existsStudentByEmail(String.valueOf(httpSession.getAttribute("studentEmail")))) {
			editStudent = studentServiceImpl.getStudentByEmail(String.valueOf(httpSession.getAttribute("studentEmail")));
			model.addAttribute("student",editStudent);  
			return "/student/s-edit";
		}
		return "redirect:/s-profile";   
	}
	
	@PostMapping("/sregister")
	public String submitStudentRegister(@ModelAttribute Student student , HttpSession httpSession
			, @RequestParam(name="g-recaptcha-response") String captcha) {
		if(!studentServiceImpl.validateStudentRegisterData(httpSession, student)) {
			return "/student/s-register"; 
		}
		else { 
			if(validator.validateCaptcha(captcha)) { 
				studentServiceImpl.addStudent(student);
				httpSession.setAttribute("studentEmail",student.getEmail());
				httpSession.setMaxInactiveInterval(24 * 60 * 60); 
				httpSession.setAttribute("sMessage","دانشجو با موفقیت ثبت شد"); 
				return "redirect:/s-login";
			}else {
				httpSession.setAttribute("sError","لطفا گزینه ی من ربات نیستم را انتخاب کنید"); 
				return "/student/s-register"; 	
			}
		}
	}
	
	@PostMapping("/slogin")
	public String submitStudentLogin(@ModelAttribute Student student , HttpSession httpSession
			, @RequestParam(name="g-recaptcha-response") String captcha) {
		Logs log = new Logs();
		if(!studentServiceImpl.validateStudentLoginData(httpSession, student)) {
			return "/student/s-login"; 
		} 
		else {
			if(validator.validateCaptcha(captcha)) {
				if(studentServiceImpl.existsStudentByEmail(student.getEmail())) {
					if(studentServiceImpl.getStudentByEmail(student.getEmail()).getPassword().equals(student.getPassword())) {
						httpSession.setAttribute("studentEmail",student.getEmail());
						logsServiceImpl.addLog(log 
								.setFull_name(studentServiceImpl.getStudentByEmail(student.getEmail()).getName()+" "+
								studentServiceImpl.getStudentByEmail(student.getEmail()).getFamily()) 
								.setEmail(student.getEmail())
								.setRole("Students")
								.setLogin_time(new Date()));  
						setLog(log);
						return "redirect:/s-profile";
					}
					else {
						httpSession.setAttribute("slError","رمز عبور اشتباه است"); 
					}
				}
				else {
					httpSession.setAttribute("slError","دانشجویی با این ایمیل وجود ندارد"); 
				}
				return "/student/s-login";  
		}
		else {
			httpSession.setAttribute("slError","لطفا گزینه ی من ربات نیستم را انتخاب کنید");  
			return "/student/s-login";
		}
		}
	}
	
	@PostMapping("/sedit")
	public String submitStudentEdit(@ModelAttribute Student student , HttpSession httpSession) {
		if(!studentServiceImpl.validateStudentUpdateData(httpSession, student)) {
			return "/student/s-edit"; 
		}
		else if(studentServiceImpl.CheckingStudentDataChanged(httpSession, student)) { 
			return "redirect:/s-profile" ;
		}
		else {
			if(httpSession.getAttribute("studentEmail").equals(student.getEmail())) {
				this.editStudent = studentServiceImpl.getStudentByEmail(String.valueOf(httpSession.getAttribute("studentEmail")));   
				student.setId(this.editStudent.getId()); 
				studentServiceImpl.addStudent(student);
				httpSession.setAttribute("esMessage","اطلاعات دانشجو با موفقیت ویرایش شد");
			}
			else {
				if(studentServiceImpl.existsStudentByEmail(student.getEmail())) {
					httpSession.setAttribute("esError","دانشجویی با این ایمیل وجود دارد"); 
					return "/student/s-edit";
				} 
				else {
					this.editStudent = studentServiceImpl.getStudentByEmail(String.valueOf(httpSession.getAttribute("studentEmail")));
					studentServiceImpl.deleteStudentById(this.editStudent.getId()); 
					studentServiceImpl.addStudent(student);
					httpSession.setAttribute("studentEmail",student.getEmail());
					httpSession.setAttribute("esMessage","اطلاعات دانشجو با موفقیت ویرایش شد"); 
				}
			}
			return "redirect:/s-profile" ;
		}
	}
	
	@PostMapping("/slogout")
	public String submitStudentLogout(HttpSession httpSession) {
		logsServiceImpl.addLog(getLog() 
				.setLogout_time(new Date()));  
		httpSession.removeAttribute("lecturerEmail");
		httpSession.removeAttribute("studentEmail");
		return "redirect:/s-home"; 
	}
	
	@PostMapping("/sdelete")
	public String submitStudentDeleteAccount(HttpSession httpSession) {
		deleteStudent = studentServiceImpl.getStudentByEmail(String.valueOf(httpSession.getAttribute("studentEmail")));
		studentServiceImpl.deleteStudentById(deleteStudent.getId());
		httpSession.removeAttribute("studentEmail");
		httpSession.setAttribute("sDeleteMessage","حساب کاربری با موفقیت حذف شد");
		return "redirect:/s-home";
	}
	
	@GetMapping("/s-forget")
	public String showStudentForgetPasswordPage(Model model) {
		model.addAttribute("student",forgetPasswordStudent);
		return "/student/s-forgetPassword"; 
	}
	
	@PostMapping("/sforget")
	public String submitLecturerForgetPassword(@ModelAttribute Student student , HttpSession httpSession) {
		if(!studentServiceImpl.validateStudentForgetPasswordData(httpSession, student)) { 
			return "/student/s-forgetPassword";
		} 
		else {
			if(studentServiceImpl.existsStudentByEmail(student.getEmail())){
				if(!passwordValidator.validatePassword(student.getPassword(),"FP_STUDENT",httpSession)){
					return "/student/s-forgetPassword";
				}
				else {
					if(student.getPassword().equals(student.getName())) {
						forgetPasswordStudent = studentServiceImpl.getStudentByEmail(student.getEmail());
						forgetPasswordStudent.setPassword(student.getPassword());
						studentServiceImpl.addStudent(forgetPasswordStudent);
						System.out.println(forgetPasswordStudent.getId()); 
						httpSession.setAttribute("sMessage","رمز عبور دانشجو با موفقیت تغییر یافت"); 
						return "redirect:/s-login"; 
					}
					else {
						httpSession.setAttribute("fsError","تکرار رمز عبور اشتباه است");
						return "/student/s-forgetPassword";
					}
				}
			}
			else {
				httpSession.setAttribute("fsError","دانشجویی با این ایمیل وجود ندارد");  
				return "/student/s-forgetPassword"; 
			}
		}
	}

}
