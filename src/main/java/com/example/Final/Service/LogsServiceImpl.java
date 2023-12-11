package com.example.Final.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Final.Entity.Logs;
import com.example.Final.Repository.LogsRepository;

@Service
public class LogsServiceImpl implements LogsService{

	@Autowired
	private LogsRepository logsRepository ;
	
	@Override
	public void addLog(Logs logs) {
		logsRepository.save(logs); 
	}

	@Override
	public Logs getLogByEmail(String email) {
		return logsRepository.getByEmail(email); 
	}

	@Override
	public List<Logs> getAllLogs() {
		return logsRepository.findAll();
	}

	@Override
	public void deleteLogById(int id) {
		logsRepository.deleteById(id);  
	}

}
