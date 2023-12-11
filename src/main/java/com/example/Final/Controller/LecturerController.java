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

import com.example.Final.Entity.Lecturer;
import com.example.Final.Entity.Logs;
import com.example.Final.Service.LecturerServiceImpl;
import com.example.Final.Service.LogsServiceImpl;
import com.example.Final.Service.PasswordValidator;
import com.example.Final.Service.ReCaptchaValidationService;

@Controller
public class LecturerController {

	// Instance Variables 
	
	@Autowired
	private LecturerServiceImpl lecturerServiceImpl ;
	@Autowired
	private Lecturer registerLecturer ;
	@Autowired
	private Lecturer loginLecturer ;
	@Autowired
	private Lecturer editLecturer ;
	@Autowired
	private Lecturer deleteLecturer ;
	@Autowired
	private Lecturer forgetPasswordLecturer ;
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
	
	@GetMapping("/l-register") 
	public String showLecturerRegisterPage(Model model) {
		model.addAttribute("lecturer",registerLecturer); 
		return "/lecturer/l-register"; 
	}
	
	@GetMapping("/l-home")
	public String showLecturerHome() {
		return "/lecturer/l-home";
	}
	
	@GetMapping("/l-login")
	public String showLecturerLoginPage(Model model) {
		model.addAttribute("lecturer",loginLecturer); 
		return "/lecturer/l-login"; 
	}
	
	@GetMapping("/l-profile")
	public String showLecturerProfile() {
		return "/lecturer/l-profile";
	}
	
	@GetMapping("/l-edit")
	public String showEmployeeEditPage(HttpSession httpSession , Model model) {
		if(lecturerServiceImpl.existsLecturerByEmail(String.valueOf(httpSession.getAttribute("lecturerEmail")))) {
			editLecturer = lecturerServiceImpl.getLecturerByEmail(String.valueOf(httpSession.getAttribute("lecturerEmail")));
			model.addAttribute("lecturer",editLecturer);
			return "/lecturer/l-edit";
		}
		return "redirect:/l-profile";  
	}
	
	@PostMapping("/lregister")
	public String submitLecturerRegister(@ModelAttribute Lecturer lecturer,HttpSession httpSession
			, @RequestParam(name="g-recaptcha-response") String captcha) {
		if(!lecturerServiceImpl.validateLecturerRegisterData(httpSession, lecturer)) {  
			return "/lecturer/l-register";
		}
		else {
			if(validator.validateCaptcha(captcha)) { 
				lecturerServiceImpl.addLecturer(lecturer); 
				httpSession.setAttribute("lecturerEmail",lecturer.getEmail());
				httpSession.setMaxInactiveInterval(24 * 60 * 60);
				httpSession.setAttribute("lMessage","استاد با موفقیت ثبت شد");  
				return "redirect:/l-login"; 
	        }
			else {
				httpSession.setAttribute("lError","لطفا گزینه ی من ربات نیستم را انتخاب کنید"); 
				return "/lecturer/l-register";
			}
		}
	}
	
	@PostMapping("/llogin")
	public String submitLecturerLogin(@ModelAttribute Lecturer lecturer , HttpSession httpSession
			, @RequestParam(name="g-recaptcha-response") String captcha) {
		Logs log = new Logs();
		if(!lecturerServiceImpl.validateLecturerLoginData(httpSession, lecturer)) {
			return "/lecturer/l-login";
		} 
		else {
			if(validator.validateCaptcha(captcha)) {
				if(lecturerServiceImpl.existsLecturerByEmail(lecturer.getEmail())) {
					if(lecturerServiceImpl.getLecturerByEmail(lecturer.getEmail()).getPassword().equals(lecturer.getPassword())) {  
						httpSession.setAttribute("lecturerEmail",lecturer.getEmail());
						logsServiceImpl.addLog(log 
								.setFull_name(lecturerServiceImpl.getLecturerByEmail(lecturer.getEmail()).getName()+" "+
								lecturerServiceImpl.getLecturerByEmail(lecturer.getEmail()).getFamily()) 
								.setEmail(lecturer.getEmail())
								.setRole("Lecturer")
								.setLogin_time(new Date()));  
						setLog(log);
						return "redirect:/l-profile"; 
					} 
					else {
						httpSession.setAttribute("llError","رمز عبور اشتباه است"); 
					}
				}
				else {
					httpSession.setAttribute("llError","استادی با این ایمیل وجود ندارد");  
				}
				return "/lecturer/l-login";  
		}
		else {
			httpSession.setAttribute("llError","لطفا گزینه ی من ربات نیستم را انتخاب کنید");  
			return "/lecturer/l-login";
		}
		}
	}
	
	/*@PostMapping("/ledit") 
	public String submitLecturerEdit(@ModelAttribute Lecturer lecturer , HttpSession httpSession) {
		if(!lecturerServiceImpl.validateLecturerUpdateData(httpSession, lecturer)) {
			return "/lecturer/l-edit"; 
		}
		else {
			if(httpSession.getAttribute("lecturerEmail").equals(lecturer.getEmail())) {
				this.editLecturer = lecturerServiceImpl.getLecturerByEmail(String.valueOf(httpSession.getAttribute("lecturerEmail")));   
				lecturer.setId(this.editLecturer.getId()); 
				lecturerServiceImpl.addLecturer(lecturer);
				httpSession.setAttribute("elMessage","اطلاعات استاد با موفقیت ویرایش شد"); System.out.println("1."+lecturer.getPhone()+" "+lecturer.getId());
			}
			else {
				if(lecturerServiceImpl.existsLecturerByEmail(lecturer.getEmail())) {
					httpSession.setAttribute("elError","استادی با این ایمیل وجود دارد"); 
					return "/lecturer/l-edit";
				} 
				else {
					this.editLecturer = lecturerServiceImpl.getLecturerByEmail(String.valueOf(httpSession.getAttribute("lecturerEmail")));
					lecturerServiceImpl.deleteLecturerById(this.editLecturer.getId());
					lecturerServiceImpl.addLecturer(lecturer);
					httpSession.setAttribute("lecturerEmail",lecturer.getEmail());
					httpSession.setAttribute("elMessage","اطلاعات کارمند با موفقیت ویرایش شد");  
				}
			}
			return "redirect:/l-profile" ;
		}
	}*/
	
	@PostMapping("/ledit") 
	public String submitLecturerEdit(@ModelAttribute Lecturer lecturer , HttpSession httpSession) {
		if(!lecturerServiceImpl.validateLecturerUpdateData(httpSession, lecturer)) {
			return "/lecturer/l-edit"; 
		}
		else if(lecturerServiceImpl.CheckingLecturerDataChanged(httpSession, lecturer)) {
			return "redirect:/l-profile" ;
		}
		else {
			if(httpSession.getAttribute("lecturerEmail").equals(lecturer.getEmail())) {
				this.editLecturer = lecturerServiceImpl.getLecturerByEmail(String.valueOf(httpSession.getAttribute("lecturerEmail")));   
				lecturer.setId(this.editLecturer.getId()); 
				lecturerServiceImpl.addLecturer(lecturer);
				httpSession.setAttribute("elMessage","اطلاعات استاد با موفقیت ویرایش شد"); 
			}
			else {
				if(lecturerServiceImpl.existsLecturerByEmail(lecturer.getEmail())) {
					httpSession.setAttribute("elError","استادی با این ایمیل وجود دارد"); 
					return "/lecturer/l-edit";
				} 
				else {
					this.editLecturer = lecturerServiceImpl.getLecturerByEmail(String.valueOf(httpSession.getAttribute("lecturerEmail")));
					lecturerServiceImpl.deleteLecturerById(this.editLecturer.getId());
					lecturerServiceImpl.addLecturer(lecturer);
					httpSession.setAttribute("lecturerEmail",lecturer.getEmail());
					httpSession.setAttribute("elMessage","اطلاعات کارمند با موفقیت ویرایش شد");  
				}
			}
			return "redirect:/l-profile" ;
		}
	}
	
	@PostMapping("/llogout")
	public String submitLecturerLogout(HttpSession httpSession) {
		logsServiceImpl.addLog(getLog() 
				.setLogout_time(new Date()));  
		httpSession.removeAttribute("lecturerEmail"); 
		return "redirect:/l-home"; 
	} 
	
	@PostMapping("/ldelete")
	public String submitLecturerDeleteAccount(HttpSession httpSession) {
		deleteLecturer = lecturerServiceImpl.getLecturerByEmail(String.valueOf(httpSession.getAttribute("lecturerEmail")));
		lecturerServiceImpl.deleteLecturerById(deleteLecturer.getId());
		httpSession.removeAttribute("lecturerEmail"); 
		httpSession.setAttribute("lDeleteMessage","حساب کاربری با موفقیت حذف شد"); 
		return "redirect:/l-home"; 
	}
	
	@GetMapping("/l-forget")
	public String showLecturerForgetPasswordPage(Model model) {
		model.addAttribute("lecturer",forgetPasswordLecturer);
		return "/lecturer/l-forgetPassword";
	}
	
	@PostMapping("/lforget")
	public String submitLecturerForgetPassword(@ModelAttribute Lecturer lecturer , HttpSession httpSession) {
		if(!lecturerServiceImpl.validateLecturerForgetPasswordData(httpSession, lecturer)) {
			return "/lecturer/l-forgetPassword"; 
		}
		else {
			if(lecturerServiceImpl.existsLecturerByEmail(lecturer.getEmail())){
				if(!passwordValidator.validatePassword(lecturer.getPassword(),"FP_LECTURER",httpSession)){
					return "/lecturer/l-forgetPassword";
				}
				else {
					if(lecturer.getPassword().equals(lecturer.getName())) {
						forgetPasswordLecturer = lecturerServiceImpl.getLecturerByEmail(lecturer.getEmail());
						forgetPasswordLecturer.setPassword(lecturer.getPassword());
						lecturerServiceImpl.addLecturer(forgetPasswordLecturer);
						System.out.println(forgetPasswordLecturer.getId()); 
						httpSession.setAttribute("lMessage","رمز عبور استاد با موفقیت تغییر یافت"); 
						return "redirect:/l-login";
					}
					else {
						httpSession.setAttribute("flError","تکرار رمز عبور اشتباه است");
						return "/lecturer/l-forgetPassword";
					}
				}
			}
			else {
				httpSession.setAttribute("flError","استادی با این ایمیل وجود ندارد"); 
				return "/lecturer/l-forgetPassword";
			}
		}
	}
	
}
