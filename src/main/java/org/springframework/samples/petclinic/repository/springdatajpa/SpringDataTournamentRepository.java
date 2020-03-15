package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.junit.experimental.categories.Categories;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.repository.TournamentRepository;

public interface SpringDataTournamentRepository extends TournamentRepository, Repository<Tournament, String>{

	
	
	
}
