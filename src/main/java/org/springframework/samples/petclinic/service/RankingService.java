package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Ranking;
import org.springframework.samples.petclinic.repository.RankingRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RankingService {

	private RankingRepository rankingRepository;
	
	@Autowired
	public RankingService(RankingRepository rankingRepository) {
		this.rankingRepository = rankingRepository;
	}

	@Transactional(readOnly = true)
	public Ranking findRankingById(int id) throws DataAccessException {
		return rankingRepository.findById(id);
	}

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void saveRanking(Ranking ranking) throws DataAccessException {
		rankingRepository.save(ranking);                
	}

	public Collection<Ranking> findRankingByTournamentId(int tournamentId) {		
		return this.rankingRepository.findByTournamentId(tournamentId);
	}

}
