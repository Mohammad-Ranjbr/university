package com.example.Final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Final.Entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer>{ 

	public boolean existsByEmail(String email); 
	public Admin getByEmail(String email);
	
}
