package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.repository.TournamentRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicateCategoryNameException;
import org.springframework.samples.petclinic.service.exceptions.WrongDateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class TournamentService {

	private TournamentRepository tournamentRepository;

	@Autowired
	public TournamentService(TournamentRepository tournamentRepository) {
		this.tournamentRepository = tournamentRepository;
	}

	/*
	 * 
	 * @Transactional public void saveTournament(Tournament tournament) throws
	 * DataAccessException { tournamentRepository.save(tournament); }
	 */

	@Transactional(rollbackFor = WrongDateException.class)
	public void saveTournament(Tournament tournament) throws DataAccessException, WrongDateException {
		// Category otherCategory = getCategorywithIdDifferent(category.getName(),
		// category.getId());
		if (tournament.getApplyDate().isAfter(tournament.getStartDate())
				|| tournament.getApplyDate().isAfter(tournament.getEndDate())
				|| tournament.getStartDate().isAfter(tournament.getEndDate())) {
			throw new WrongDateException();
		} else
			tournamentRepository.save(tournament);
	}

}
