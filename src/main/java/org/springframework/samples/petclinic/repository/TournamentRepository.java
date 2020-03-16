package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Tournament;

public interface TournamentRepository {

    void save(Tournament tournament) throws DataAccessException;
    
    Tournament findById(int id) throws DataAccessException;
    
    
	
}
