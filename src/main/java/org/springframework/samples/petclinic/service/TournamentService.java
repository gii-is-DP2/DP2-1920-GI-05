package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.repository.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TournamentService {

	private TournamentRepository tournamentRepository;

	@Autowired
	public TournamentService(TournamentRepository tournamentRepository) {
		this.tournamentRepository = tournamentRepository;
	}

	@Transactional
	public void saveTournament(Tournament tournament) throws DataAccessException {
		tournamentRepository.save(tournament);
	}

	@Transactional(readOnly = true)
	public Tournament findTournamentById(int id) throws DataAccessException {
		return tournamentRepository.findById(id);
	}
	
	
	public Collection<Tournament> findAllTournament() throws DataAccessException {
		return tournamentRepository.findAllTournament();
	}

	public Collection<Tournament> findActiveTournaments() throws DataAccessException {
		return tournamentRepository.findActiveTournaments();
	}


}
