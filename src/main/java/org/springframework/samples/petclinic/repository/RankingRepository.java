package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Ranking;

public interface RankingRepository {

	Ranking findById(int id) throws DataAccessException;

	void save(Ranking ranking) throws DataAccessException;

	List<Ranking> findByTournamentId(int tournamentId) throws DataAccessException;	

}
