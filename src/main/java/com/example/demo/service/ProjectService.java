package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository repo;
	
	public List<Project> listAll() {
		
		
		return repo.findAll();
		
		
	}
	
	public void save(Project project) {
		repo.save(project);
	}
	
	public Project get(Long id) {
		return repo.findById(id).get();
	}
	

	public void delete(Long id) {
		repo.deleteById(id);
	}


	
//	public void save() {
//		
//Iterable<Project> projects = repo.findAll();
//		
//		for (Project project: projects) {
//
//			float totalPledged = project.getAmount();
//			
//			if (totalPledged >= project.getAmountreceived()) {
//					repo.delete(project);
//			}
//			repo.save(project);
//		}
//	}
		
	}

