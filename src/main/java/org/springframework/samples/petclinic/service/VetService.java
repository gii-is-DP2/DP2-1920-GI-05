package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VetService {

	private VetRepository vetRepository;


	@Autowired
	public VetService(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}		

	@Transactional(readOnly = true)	
	public Collection<Vet> findVets() throws DataAccessException {
		return vetRepository.findAll();
	}	

}
