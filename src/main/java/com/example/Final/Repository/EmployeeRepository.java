package com.example.Final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Final.Entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer>{ 

	public boolean existsByEmail(String email);
	public boolean existsByPhone(String phone);
	public Employee getByEmail(String email); 
	
}
