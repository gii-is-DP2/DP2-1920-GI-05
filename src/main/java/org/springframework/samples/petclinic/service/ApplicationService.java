package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationService {

	private ApplicationRepository applicationRepository;

	@Autowired
	public ApplicationService(ApplicationRepository applicationRepository) {
		this.applicationRepository = applicationRepository;
	}
	
	@Transactional
	public void saveApplication(Application application) throws DataAccessException {
		applicationRepository.save(application);
	}

	@Transactional(readOnly = true)
	public Application findApplicationById(int id) throws DataAccessException {
		return applicationRepository.findById(id);
	}

	@Transactional
	public Collection<Application> findApplicationsByOwnerId(int ownerId) {
		return applicationRepository.findApplicationsByOwnerId(ownerId);
	}

}
