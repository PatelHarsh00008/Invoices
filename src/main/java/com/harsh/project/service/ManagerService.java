package com.harsh.project.service;

import java.util.List;

import com.harsh.project.entities.Manager;

public interface ManagerService {
	List<Manager> getAllManagers();
	Manager getManagerById(long id);
	Manager updateManager(Manager manager, long id);
	Manager createManager(Manager manager);
	void deleteManager(long id);
	List<Manager> getAllManagersByProjectId(long id);
}
