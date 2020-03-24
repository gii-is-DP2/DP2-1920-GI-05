package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Jugde;
import org.springframework.samples.petclinic.repository.JugdeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JugdeService {
	
	private JugdeRepository jugdeRepository;
	
	@Autowired
	public JugdeService(JugdeRepository jugdeRepository) {
		this.jugdeRepository = jugdeRepository;
	}
	
	@Transactional(readOnly = true)
	public Collection<Jugde> findJudges() throws DataAccessException {
		return jugdeRepository.findAll();
	}

}
