package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Ranking;
import org.springframework.samples.petclinic.repository.RankingRepository;

public interface SpringDataRankingRepository extends RankingRepository, Repository<Ranking, Integer> {
	
	@Query("SELECT  r FROM Ranking r")
	public List<Ranking> findAllRanking();

	@Query("SELECT ranking FROM Ranking ranking WHERE ranking.tournament.id =:tournamentId")
	public List<Ranking> findByTournamentId(@Param("tournamentId") int tournamentId);

}
