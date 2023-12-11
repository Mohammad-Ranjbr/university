package com.example.Final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Final.Entity.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer,Integer>{

	public boolean existsByEmail(String email);
	public boolean existsByPhone(String phone);
	public Lecturer getByEmail(String email); 
	
}
