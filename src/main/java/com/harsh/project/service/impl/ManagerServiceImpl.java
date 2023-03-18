package com.harsh.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsh.project.entities.Manager;
import com.harsh.project.exceptions.ResourceNotFoundException;
import com.harsh.project.respositories.ManagerRepository;
import com.harsh.project.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService{

	@Autowired
	private ManagerRepository managerRepository;
	
	@Override
	public List<Manager> getAllManagers() {
		return this.managerRepository.findAll();
	}

	@Override
	public Manager getManagerById(long id) {
		Manager manager = this.managerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
		return manager;
	}

	@Override
	public Manager updateManager(Manager manager, long id) {
		Manager m = this.managerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
		m.setName(manager.getName());
		m.setProject(manager.getProject());
		return this.managerRepository.save(m);
		
	}

	@Override
	public Manager createManager(Manager manager) {
		return this.managerRepository.save(manager);
	}

	@Override
	public void deleteManager(long id) {
		Manager manager = this.managerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
		this.managerRepository.delete(manager);
	}

	@Override
	public List<Manager> getAllManagersByProjectId(long id) {
		List<Manager> managers = this.managerRepository.getAllManagersByProjectId(id);
		if(managers.size() == 0) throw new ResourceNotFoundException("Managers", "project id", id);
		return managers;
	}

}
