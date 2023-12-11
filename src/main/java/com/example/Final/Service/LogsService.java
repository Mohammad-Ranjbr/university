package com.example.Final.Service;

import java.util.List;

import com.example.Final.Entity.Logs;

public interface LogsService {

	public void addLog(Logs logs);
	public Logs getLogByEmail(String email);
	public List<Logs> getAllLogs();
	public void deleteLogById(int id);
	
}
