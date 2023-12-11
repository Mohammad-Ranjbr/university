package com.example.Final.Service;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Final.Entity.Student;
import com.example.Final.Repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentRepository studentRepository ;
	@Autowired
	private PasswordValidator passwordValidator ;
	@Autowired
	private PhoneValidator phoneValidator ;
	
	@Override
	public void addStudent(Student student) {
		studentRepository.save(student); 
	}

	@Override
	public boolean existsStudentByEmail(String email) {
		return studentRepository.existsByEmail(email);
	}

	@Override
	public boolean existsStudentByPhone(String phone) {
		return studentRepository.existsByPhone(phone); 
	}

	@Override
	public Student getStudentByEmail(String email) {
		return studentRepository.getByEmail(email);  
	}

	@Override
	public void deleteStudentById(int id) {
		studentRepository.deleteById(id); 
	}

	@Override
	public boolean validateStudentRegisterData(HttpSession httpSession, Student student) {
		if(student.getName().trim().isEmpty()) {
			httpSession.setAttribute("sError","لطفا نام را وارد کنید");
			return false;
		}
		else if(student.getFamily().trim().isEmpty()) {
			httpSession.setAttribute("sError","لطفا نام خانوادگی را وارد کنید");
			return false;
		}
		else if(student.getMeli_code().trim().isEmpty()) {
			httpSession.setAttribute("sError","لطفا کد ملی را وارد کنید");
			return false;
		}
		else if(student.getEmail().trim().isEmpty()) {
			httpSession.setAttribute("sError","لطفا ایمیل را وارد کنید");
			return false;
		}
		else if(!student.getEmail().trim().matches("^.+@.+\\..+$") ) {
			httpSession.setAttribute("sError","لطفا '@' را در آدرس ایمیل وارد کنید");
			return false;
		}
		else if(studentRepository.existsByEmail(student.getEmail())) {
			httpSession.setAttribute("sError","این ایمیل از قبل وجود دارد");
			return false; 
		}
		else if(!phoneValidator.validatePhoneNumber(student.getPhone(),"R_STUDENT",httpSession)) {
			return false ;
		}
		else if(student.getPhone().trim().isEmpty()) {
			httpSession.setAttribute("sError","لطفا شماره موبایل را وارد کنید");
			return false;
		}
		else if(student.getStudent_number().trim().isEmpty()) {
			httpSession.setAttribute("sError","لطفا شماره دانشجویی را وارد کنید");
			return false;
		}
		else if(student.getStart_date().trim().isEmpty()) {
			httpSession.setAttribute("sError","لطفا سال شروع را انتخاب کنید"); 
			return false;
		}
		else if(student.getAddress().trim().isEmpty()) {
			httpSession.setAttribute("sError","لطفا ادرس را وارد کنید");
			return false;
		}
		else if(!passwordValidator.validatePassword(student.getPassword(),"R_STUDENT", httpSession)) {
			return false ;
		}
		else if(student.getGender().trim().isEmpty()) {
			httpSession.setAttribute("sError","لطفا جنسیت را انتخاب کنید"); 
			return false;
		}
		return true ;
	}

	@Override
	public boolean validateStudentLoginData(HttpSession httpSession, Student student) {
		if(student.getEmail().trim().isEmpty()) {
			httpSession.setAttribute("slError","لطفا ایمیل را وارد کنید");
			return false;
		}
		else if(!student.getEmail().matches("^.+@.+\\..+$") ) {
			httpSession.setAttribute("slError","لطفا '@' را در آدرس ایمیل وارد کنید");
			return false;
		} 
		else if(student.getPassword().trim().isEmpty()) {
			httpSession.setAttribute("slError","لطفا رمز عبور را وارد کنید");
			return false;
		}
		return true ;
	}

	@Override
	public boolean validateStudentUpdateData(HttpSession httpSession, Student student) {
		if(student.getName().trim().isEmpty()) {
			httpSession.setAttribute("esError","لطفا نام را وارد کنید");
			return false;
		}
		else if(student.getFamily().trim().isEmpty()) {
			httpSession.setAttribute("esError","لطفا نام خانوادگی را وارد کنید");
			return false;
		}
		else if(student.getMeli_code().trim().isEmpty()) {
			httpSession.setAttribute("esError","لطفا کد ملی را وارد کنید");
			return false;
		}
		else if(student.getEmail().trim().isEmpty()) {
			httpSession.setAttribute("esError","لطفا ایمیل را وارد کنید");
			return false;
		}
		else if(!student.getEmail().matches("^.+@.+\\..+$") ) {
			httpSession.setAttribute("esError","لطفا '@' را در آدرس ایمیل وارد کنید");
			return false ;
		}
		else if(!phoneValidator.validatePhoneNumber(student.getPhone(),"E_STUDENT",httpSession) ) {
			return false ; 
		}
		else if (studentRepository.existsByPhone(student.getPhone()) && !getStudentById(student.getId()).getPhone().equals(student.getPhone())) {
			httpSession.setAttribute("esError","این شماره از قبل وجود دارد"); 
			return false;
		}
		else if(student.getStudent_number().trim().isEmpty()) {
			httpSession.setAttribute("esError","لطفا شماره دانشجویی را وارد کنید");
			return false;
		}
		else if(student.getStart_date().trim().isEmpty()) {
			httpSession.setAttribute("esError","لطفا سال شروع را انتخاب کنید"); 
			return false;
		}
		else if(student.getAddress().trim().isEmpty()) {
			httpSession.setAttribute("esError","لطفا ادرس را وارد کنید");
			return false;
		}
		else if(!passwordValidator.validatePassword(student.getPassword(),"E_STUDENT",httpSession) && 
				!getStudentById(student.getId()).getPassword().equals(student.getPassword())) {
			return false ; 
		}
		else if(student.getGender().trim().isEmpty()) {
			httpSession.setAttribute("esError","لطفا جنسیت را انتخاب کنید"); 
			return false;
		}
		return true ;
	}

	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student getStudentById(int id) {
		Optional<Student> student = studentRepository.findById(id);
		if(student.isPresent()) {
			return student.get();
		}
		return null;
	}

	@Override
	public boolean validateStudentForgetPasswordData(HttpSession httpSession, Student student) {
		if(student.getEmail().trim().isEmpty()) {
			httpSession.setAttribute("fsError","لطفا ایمیل را وارد کنید");
			return false;
		}
		else if(!student.getEmail().matches("^.+@.+\\..+$") ) {
			httpSession.setAttribute("fsError","لطفا '@' را در آدرس ایمیل وارد کنید");
			return false;
		} 
		return true ;
	}

	@Override
	public boolean CheckingStudentDataChanged(HttpSession httpSession, Student student) {
		if(getStudentById(student.getId()).getName().equals(student.getName()) &&
				getStudentById(student.getId()).getFamily().equals(student.getFamily()) &&
				getStudentById(student.getId()).getMeli_code().equals(student.getMeli_code()) &&
				getStudentById(student.getId()).getEmail().equals(student.getEmail()) &&
				getStudentById(student.getId()).getPhone().equals(student.getPhone()) &&
				getStudentById(student.getId()).getAddress().equals(student.getAddress()) &&
				getStudentById(student.getId()).getGender().equals(student.getGender()) &&
				getStudentById(student.getId()).getPassword().equals(student.getPassword()) &&
				getStudentById(student.getId()).getStudent_number().equals(student.getStudent_number()) &&
				getStudentById(student.getId()).getStart_date().equals(student.getStart_date())) {
			httpSession.setAttribute("cesMessage","تغییری در اطلاعات رخ نداده است");
			return true ;
		}
		return false ;
	}

}
