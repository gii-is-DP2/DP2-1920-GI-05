package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Jugde;

public interface JugdeRepository {
	
	Collection<Jugde> findAll() throws DataAccessException;
	
	

}
