package com.example.Final.Controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.Final.Entity.Admin;
import com.example.Final.Entity.Employee;
import com.example.Final.Entity.Lecturer;
import com.example.Final.Entity.Logs;
import com.example.Final.Entity.Student;
import com.example.Final.Service.AdminServiceImpl;
import com.example.Final.Service.EmployeeServiceImpl;
import com.example.Final.Service.LecturerServiceImpl;
import com.example.Final.Service.LogsServiceImpl;
import com.example.Final.Service.StudentServiceImpl;

@Controller
public class AdminController {

	// Instance Variables 
	
	@Autowired
	private LecturerServiceImpl lecturerServiceImpl ;
	@Autowired
	private EmployeeServiceImpl employeeServiceImpl ;
	@Autowired
	private StudentServiceImpl studentServiceImpl ;
	@Autowired
	private AdminServiceImpl adminServiceImpl ;
	@Autowired
	private LogsServiceImpl logsServiceImpl ;
	@Autowired
	private Admin loginAdmin ;
	@Autowired
	private Lecturer registerLecturer ;
	@Autowired
	private Employee registerEmployee ;
	@Autowired
	private Student registerStudent ;
	@Autowired
	private Lecturer editLecturer ;
	@Autowired
	private Employee editEmployee ;
	@Autowired
	private Student editStudent ;
	@Autowired
	private Logs logs ;
	
	// Setters 
	
	private void setLog(Logs log) {
		this.logs = log ;
	}
	
	// Getters 
	
	private Logs getLog() {
		return logs ;
	}
	
	@GetMapping("/admin")
	public String showAdminHome(Model model) {
		model.addAttribute("admin",loginAdmin); 
		return "/admin/a-login";  
	}
	
	@GetMapping("/lecturers")
	public String showLecturersList(Model model) {
		List<Lecturer> lecturers = lecturerServiceImpl.getAllLecturers();
		model.addAttribute("lecturers",lecturers); 
		return "/admin/a-lecturers"; 
	}
	
	@GetMapping("/adminhome")
	public String showAdminHome() {
		return "/admin/a-home"; 
	}
	
	@GetMapping("/employees")
	public String showEmployeesList(Model model) {
		List<Employee> employees = employeeServiceImpl.getAllEmployees();
		model.addAttribute("employees",employees);
		return "/admin/a-employees"; 
	}
	
	@GetMapping("/students")
	public String showStudentsList(Model model) {
		List<Student> students = studentServiceImpl.getAllStudents();
		model.addAttribute("students",students);
		return "/admin/a-students"; 
	}
	
	@GetMapping("/logs")
	public String showLogsList(Model model) {
		List<Logs> logs = logsServiceImpl.getAllLogs();
		model.addAttribute("logs",logs); 
		return "/admin/a-logs"; 
	}
	
	@GetMapping("/a-lecturers")
	public String showLecturersList2(Model model) {
		List<Lecturer> lecturers = lecturerServiceImpl.getAllLecturers();
		model.addAttribute("lecturers",lecturers); 
		return "/admin/a-lecturers"; 
	}
	
	@GetMapping("/ldelete/{id}")
	public String submitLecturerDelete(@PathVariable int id , HttpSession httpSession) {
		lecturerServiceImpl.deleteLecturerById(id);
		httpSession.setAttribute("lecturerAdminMessage","استاد با موفقیت حذف شد"); 
		return "redirect:/a-lecturers";  
	}
	
	@GetMapping("/a-employee")
	public String showEmployeesList2(Model model) {
		List<Employee> employees = employeeServiceImpl.getAllEmployees();
		model.addAttribute("employees",employees);
		return "/admin/a-employees";
	}

	@GetMapping("/edelete/{id}")
	public String submitEmployeeDelete(@PathVariable int id , HttpSession httpSession) {
		employeeServiceImpl.deleteEmployeeById(id);
		httpSession.setAttribute("employeeAdminMessage","کارمند با موفقیت حذف شد");
		return "redirect:/a-employee";
	}
	
	@GetMapping("/a-students")
	public String showStudentsList2(Model model) {
		List<Student> students = studentServiceImpl.getAllStudents();
		model.addAttribute("students",students);
		return "/admin/a-students";
	}
	
	@GetMapping("/sdelete/{id}")
	public String submitStudentDelete(@PathVariable int id , HttpSession httpSession) {
		studentServiceImpl.deleteStudentById(id);
		httpSession.setAttribute("studentAdminMessage","دانشجو با موفقیت حذف شد");
		return "redirect:/a-students";
	}
	
	@GetMapping("/logdelete/{id}")
	public String submitLogsDelete(@PathVariable int id , HttpSession httpSession) {
		logsServiceImpl.deleteLogById(id);
		httpSession.setAttribute("logsadminMessage","ورود با موفقیت حذف شد"); 
		return "redirect:/logs"; 
	}
	
	@PostMapping("/alogin")
	public String submitAdminLogin(@ModelAttribute Admin admin , HttpSession httpSession) {
		Logs log = new Logs();
		if(!adminServiceImpl.validateAdminLoginData(httpSession,admin)) { 
			return "admin/a-login";
		}
		else {
			if(adminServiceImpl.existsAdminByEmail(admin.getEmail())) {
				if(adminServiceImpl.getAdminByEmail(admin.getEmail()).getPassword().equals(admin.getPassword())) {
					httpSession.setAttribute("adminEmail",admin.getEmail()); 
					logsServiceImpl.addLog(log 
							.setFull_name(adminServiceImpl.getAdminByEmail(admin.getEmail()).getName()+" "+
							adminServiceImpl.getAdminByEmail(admin.getEmail()).getFamily()) 
							.setEmail(admin.getEmail())
							.setRole("Admin")
							.setLogin_time(new Date()));  
					setLog(log);
					return "redirect:/a-home"; 
				}
				else {
					httpSession.setAttribute("alError","رمز عبور اشتباه است");
				}
			}
			else {
				httpSession.setAttribute("alError","ادمینی با این ایمیل وجود ندارد"); 
			}
			return "admin/a-login";
		}
	}
	
	@PostMapping("/alogout")
	public String submitAdminLogout(HttpSession httpSession) {
		logsServiceImpl.addLog(getLog() 
				.setLogout_time(new Date()));  
		httpSession.removeAttribute("adminEmail");  
		return "redirect:/admin"; 
	}
	
	@GetMapping("/al-register")
	public String showLecturerRegisterPage(Model model) {
		model.addAttribute("lecturer",registerLecturer); 
		return "/admin/al-register"; 
	}
	
	@PostMapping("/alregister")
	public String submitLecturerRegister(@ModelAttribute Lecturer lecturer , HttpSession httpSession) {
		if(!lecturerServiceImpl.validateLecturerRegisterData(httpSession, lecturer)) {  
			return "/admin/al-register";
		}
		else {
			lecturerServiceImpl.addLecturer(lecturer); 
			httpSession.setAttribute("alr","استاد با موفقیت ثبت شد");  
			return "redirect:/al-register"; 
		}
	}
	
	@GetMapping("/ae-register")
	public String showEmployeeRegisterPage(Model model) {
		model.addAttribute("employee",registerEmployee);
		return "/admin/ae-register"; 
	}
	
	@PostMapping("/aeregister")
	public String submitEmployeeRegister(@ModelAttribute Employee employee , HttpSession httpSession) {
		if(!employeeServiceImpl.validateEmployeeRegisterData(httpSession, employee)) {
			return "/admin/ae-register"; 
		}
		else {
			employeeServiceImpl.addEmployee(employee);
			httpSession.setAttribute("aer","کارمند با موفقیت ثبت شد");
			return "redirect:/ae-register"; 
		}
	}
	
	@GetMapping("/as-register")
	public String showStudentRegeisterPage(Model model) {
		model.addAttribute("student",registerStudent);
		return "/admin/as-register";
	}
	
	@PostMapping("/asregister")
	public String submitStudentRegister(@ModelAttribute Student student , HttpSession httpSession) {
		if(!studentServiceImpl.validateStudentRegisterData(httpSession, student)) {
			return "/admin/as-register"; 
		}
		else { 
			studentServiceImpl.addStudent(student);
			httpSession.setAttribute("asr","دانشجو با موفقیت ثبت شد"); 
			return "redirect:/as-register";
		}
	}
	
	@GetMapping("/ledit/{id}")
	public String showLecturerEditPage(@PathVariable int id , Model model) {
		editLecturer = lecturerServiceImpl.getLecturerById(id);
		model.addAttribute("lecturer",editLecturer);
		return "/admin/al-edit"; 
	}
	
	@PostMapping("/aledit")
	public String submitLecturerEdit(@ModelAttribute Lecturer lecturer , HttpSession httpSession) {
		editLecturer = lecturerServiceImpl.getLecturerById(lecturer.getId());
		if(!lecturerServiceImpl.validateLecturerUpdateData(httpSession, lecturer)) {
			return "/admin/al-edit"; 
		}
		else {
			if(editLecturer.getEmail().equals(lecturer.getEmail())) {
				lecturerServiceImpl.addLecturer(lecturer);  
				httpSession.setAttribute("lecturerAdminMessage","اطلاعات استاد با موفقیت بروزرسانی شد");  
				return "redirect:/lecturers"; 
			}
			else if(lecturerServiceImpl.existsLecturerByEmail(lecturer.getEmail())) {
				httpSession.setAttribute("elError","استادی با این ایمیل وجود دارد"); 
				return "/admin/al-edit"; 
			} 
			else {
				lecturerServiceImpl.addLecturer(lecturer);  
				httpSession.setAttribute("lecturerAdminMessage","اطلاعات استاد با موفقیت بروزرسانی شد");  
				return "redirect:/lecturers"; 
			}
		}
	}
	
	@GetMapping("/eedit/{id}")
	public String shoeEmployeeedit(@PathVariable int id , Model model) {
		editEmployee = employeeServiceImpl.getEmployeeById(id);
		model.addAttribute("employee",editEmployee);
		return "/admin/ae-edit"; 
	} 
	
	@PostMapping("/aeedit")
	public String submitEmployeeEdit(@ModelAttribute Employee employee , HttpSession httpSession) {
		editEmployee = employeeServiceImpl.getEmployeeById(employee.getId());
		if(!employeeServiceImpl.validateEmployeeUpdateData(httpSession,employee)) {
			return "/admin/ae-edit"; 
		}
		else {
			if(editEmployee.getEmail().equals(employee.getEmail())) {
				employeeServiceImpl.addEmployee(employee); 
				httpSession.setAttribute("employeeAdminMessage","اطلاعات کارمند با موفقیت بروزرسانی شد");  
				return "redirect:/employees";
			}
			else if(employeeServiceImpl.existsEmployeeByEmail(employee.getEmail())) { 
				httpSession.setAttribute("eeError","کارمندی با این ایمیل وجود دارد"); 
				return "/admin/ae-edit"; 
			} 
			else {
				employeeServiceImpl.addEmployee(employee); 
				httpSession.setAttribute("employeeAdminMessage","اطلاعات کارمند با موفقیت بروزرسانی شد");  
				return "redirect:/employees"; 
			}
		}
	}
	
	@GetMapping("/sedit/{id}")
	public String showStudentEdit(@PathVariable int id , Model model) {
		editStudent = studentServiceImpl.getStudentById(id);
		model.addAttribute("student",editStudent);
		return "/admin/as-edit"; 
	}
	
	@PostMapping("/asedit")
	public String submitStudentEmployeeEdit(@ModelAttribute Student student , HttpSession httpSession) {
		editStudent = studentServiceImpl.getStudentById(student.getId()); 
		if(!studentServiceImpl.validateStudentUpdateData(httpSession,student)) {
			return "/admin/as-edit"; 
		}
		else {
			if(editStudent.getEmail().equals(student.getEmail())) {
				studentServiceImpl.addStudent(student); 
				httpSession.setAttribute("studentAdminMessage","اطلاعات دانشجو با موفقیت بروزرسانی شد");  
				return "redirect:/students";
			}
			else if(studentServiceImpl.existsStudentByEmail(student.getEmail())) {  
				httpSession.setAttribute("esError","دانشجویی با این ایمیل وجود دارد"); 
				return "/admin/as-edit"; 
			} 
			else {
				studentServiceImpl.addStudent(student); 
				httpSession.setAttribute("studentAdminMessage","اطلاعات دانشجو با موفقیت بروزرسانی شد");  
				return "redirect:/students"; 
			}
		}
	}
	
}
