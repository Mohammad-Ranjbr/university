package com.example.Final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Final.Entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>{

	public boolean existsByEmail(String email);
	public boolean existsByPhone(String phone);
	public Student getByEmail(String email);  
	
}
