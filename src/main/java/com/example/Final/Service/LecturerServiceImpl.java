package com.example.Final.Service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Final.Entity.Lecturer;
import com.example.Final.Repository.LecturerRepository;

@Service
public class LecturerServiceImpl implements LecturerService{

	@Autowired
	private LecturerRepository lecturerRepository ; 
	@Autowired
	private PasswordValidator passwordValidator ;
	@Autowired
	private PhoneValidator phoneValidator ;
	
	@Override
	public void addLecturer(Lecturer lecturer) {
		lecturerRepository.save(lecturer); 
	}

	@Override
	public boolean existsLecturerByEmail(String email) {
		return lecturerRepository.existsByEmail(email); 
	}

	@Override
	public boolean existsLecturerByPhone(String phone) {
		return lecturerRepository.existsByPhone(phone); 
	}

	@Override
	public Lecturer getLecturerByEmail(String email) {
		return lecturerRepository.getByEmail(email); 
	}

	@Override
	public void deleteLecturerById(int id) {
		lecturerRepository.deleteById(id); 
	}

	@Override
	public boolean validateLecturerRegisterData(HttpSession httpSession, Lecturer lecturer) {
		if(lecturer.getName().trim().isEmpty()) {
			httpSession.setAttribute("lError","لطفا نام را وارد کنید");
			return false;
		}
		else if(lecturer.getFamily().trim().isEmpty()) {
			httpSession.setAttribute("lError","لطفا نام خانوادگی را وارد کنید");
			return false;
		}
		else if(lecturer.getMeli_code().trim().isEmpty()) {
			httpSession.setAttribute("lError","لطفا کد ملی را وارد کنید");
			return false;
		}
		else if(lecturer.getEmail().trim().isEmpty()) {
			httpSession.setAttribute("lError","لطفا ایمیل را وارد کنید");
			return false;
		}
		else if(!lecturer.getEmail().matches("^.+@.+\\..+$")) {
			httpSession.setAttribute("lError","لطفا '@' را در آدرس ایمیل وارد کنید");
			return false;
		}
		else if(lecturerRepository.existsByEmail(lecturer.getEmail())) {   
			httpSession.setAttribute("lError","این ایمیل از قبل وجود دارد");
			return false;
		}
		else if(!phoneValidator.validatePhoneNumber(lecturer.getPhone(),"R_LECTURER",httpSession)) {
			return false ;
		}
		else if(lecturerRepository.existsByPhone(lecturer.getPhone())) {
			httpSession.setAttribute("lError","این شماره از قبل وجود دارد");
			return false;
		}
		else if(lecturer.getLecturer_number().trim().isEmpty()) {
			httpSession.setAttribute("lError","لطفا کد پرسنلی را وارد کنید");
			return false;
		}
		else if(lecturer.getAddress().trim().isEmpty()) {
			httpSession.setAttribute("lError","لطفا ادرس را وارد کنید");
			return false;
		}
		else if(!passwordValidator.validatePassword(lecturer.getPassword(),"R_LECTURER",httpSession)) {
			return false ;
		}
		else if(lecturer.getGender().trim().isEmpty()) {
			httpSession.setAttribute("lError","لطفا جنسیت را انتخاب کنید"); 
			return false;
		}
		return true;
	}

	@Override
	public boolean validateLecturerLoginData(HttpSession httpSession, Lecturer lecturer) {
		if(lecturer.getEmail().isEmpty()) {
			httpSession.setAttribute("llError","لطفا ایمیل را وارد کنید");
			return false;
		}
		else if(!lecturer.getEmail().matches("^.+@.+\\..+$")) {
			httpSession.setAttribute("llError","لطفا '@' را در آدرس ایمیل وارد کنید");
			return false; 
		}
		else if(lecturer.getPassword().isEmpty()) {
			httpSession.setAttribute("llError","لطفا رمز عبور را وارد کنید");
			return false;
		}
		return true ;
	}

	@Override
	public boolean validateLecturerUpdateData(HttpSession httpSession, Lecturer lecturer) {
		if(lecturer.getName().trim().isEmpty()) { 
			httpSession.setAttribute("elError","لطفا نام را وارد کنید");
			return false;
		}
		else if(lecturer.getFamily().trim().isEmpty()) {
			httpSession.setAttribute("elError","لطفا نام خانوادگی را وارد کنید");
			return false;
		}
		else if(lecturer.getMeli_code().trim().isEmpty()) {
			httpSession.setAttribute("elError","لطفا کد ملی را وارد کنید");
			return false;
		}
		else if(lecturer.getEmail().trim().isEmpty()) {
			httpSession.setAttribute("elError","لطفا ایمیل را وارد کنید");
			return false;
		}
		else if(!lecturer.getEmail().matches("^.+@.+\\..+$")) {
			httpSession.setAttribute("elError","لطفا '@' را در آدرس ایمیل وارد کنید");
			return false;
		}
		else if(!phoneValidator.validatePhoneNumber(lecturer.getPhone(),"E_LECTURER",httpSession) ) {
			return false ; 
		}
		else if(lecturerRepository.existsByPhone(lecturer.getPhone()) && !getLecturerById(lecturer.getId()).getPhone().equals(lecturer.getPhone())) { 
			httpSession.setAttribute("elError","این شماره از قبل وجود دارد");
			return false;
		}
		else if(lecturer.getLecturer_number().trim().isEmpty()) {
			httpSession.setAttribute("elError","لطفا کد پرسنلی را وارد کنید");
			return false;
		}
		else if(lecturer.getAddress().trim().isEmpty()) {
			httpSession.setAttribute("elError","لطفا ادرس را وارد کنید");
			return false;
		}
		else if(!passwordValidator.validatePassword(lecturer.getPassword(),"E_LECTURER",httpSession) && 
				!getLecturerById(lecturer.getId()).getPassword().equals(lecturer.getPassword())) {
			return false ; 
		}
		else if(lecturer.getGender().trim().isEmpty()) {
			httpSession.setAttribute("elError","لطفا جنسیت را انتخاب کنید"); 
			return false;
		}
		return true;
	}

	@Override
	public List<Lecturer> getAllLecturers() {
		return lecturerRepository.findAll();
	}

	@Override
	public Lecturer getLecturerById(int id) {
		Optional<Lecturer> lecturer = lecturerRepository.findById(id);
		if(lecturer.isPresent()) {
			return lecturer.get() ; 
		}
		return null ;
	}

	@Override
	public boolean validateLecturerForgetPasswordData(HttpSession httpSession, Lecturer lecturer) {
		if(lecturer.getEmail().trim().isEmpty()) {
			httpSession.setAttribute("flError","لطفا ایمیل را وارد کنید");
			return false;
		}
		else if(!lecturer.getEmail().matches("^.+@.+\\..+$")) {
			httpSession.setAttribute("flError","لطفا '@' را در آدرس ایمیل وارد کنید");
			return false; 
		}
		return true ;
	}

	@Override
	public boolean CheckingLecturerDataChanged(HttpSession httpSession, Lecturer lecturer) {
		if(getLecturerById(lecturer.getId()).getName().equals(lecturer.getName()) &&
				getLecturerById(lecturer.getId()).getFamily().equals(lecturer.getFamily()) &&
				getLecturerById(lecturer.getId()).getMeli_code().equals(lecturer.getMeli_code()) &&
				getLecturerById(lecturer.getId()).getEmail().equals(lecturer.getEmail()) &&
				getLecturerById(lecturer.getId()).getPhone().equals(lecturer.getPhone()) &&
				getLecturerById(lecturer.getId()).getAddress().equals(lecturer.getAddress()) &&
				getLecturerById(lecturer.getId()).getGender().equals(lecturer.getGender()) &&
				getLecturerById(lecturer.getId()).getPassword().equals(lecturer.getPassword()) &&
				getLecturerById(lecturer.getId()).getLecturer_number().equals(lecturer.getLecturer_number())) {
			httpSession.setAttribute("celMessage","تغییری در اطلاعات رخ نداده است");
			return true ;
		}
		return false ;
	}
	
}
