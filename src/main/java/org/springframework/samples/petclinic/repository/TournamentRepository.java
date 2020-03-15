package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Tournament;

public interface TournamentRepository {

	
    void save(Tournament tournament) throws DataAccessException;
	
}
