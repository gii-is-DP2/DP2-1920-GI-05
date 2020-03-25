package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;
import java.util.List;

import org.junit.experimental.categories.Categories;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.repository.TournamentRepository;

public interface SpringDataTournamentRepository extends TournamentRepository, Repository<Tournament, String>{

	@Override
	@Query("SELECT  t FROM Tournament t")
	public Collection<Tournament> findAllTournament();
	
	@Override
	@Query("SELECT  t FROM Tournament t WHERE t.applyDate > current_timestamp")
	public Collection<Tournament> findActiveTournaments();
	
	@Override
	@Query("SELECT t FROM Tournament t WHERE t.name =:name")
	public List<Tournament> findByName(@Param("name") String name);
	
}
