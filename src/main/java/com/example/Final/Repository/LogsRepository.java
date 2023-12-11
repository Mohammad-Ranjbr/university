package com.example.Final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Final.Entity.Logs;

@Repository
public interface LogsRepository extends JpaRepository<Logs,Integer>{ 
	
	public Logs getByEmail(String email);

}
