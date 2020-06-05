package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Ranking;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.repository.RankingRepository;
import org.springframework.samples.petclinic.repository.TournamentRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicateTournamentNameException;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TournamentService extends Utils {

	private TournamentRepository tournamentRepository;
	private RankingRepository rankingRepository;

	@Autowired
	public TournamentService(TournamentRepository tournamentRepository, RankingRepository rankingRepository) {
		this.tournamentRepository = tournamentRepository;
		this.rankingRepository = rankingRepository;
	}

	@Transactional(rollbackFor = DuplicateTournamentNameException.class)
	public void saveTournament(Tournament tournament) throws DataAccessException, DuplicateTournamentNameException {

		Tournament other = this.tournamentRepository.findByName(tournament.getName());

		if (checkDuplicated(other, tournament)) {

			throw new DuplicateTournamentNameException();

		}

		else
			tournamentRepository.save(tournament);
	}

	@Transactional(readOnly = true)
	public Tournament findTournamentById(int id) throws DataAccessException {
		return tournamentRepository.findById(id);
	}

	@Transactional
	public Collection<Tournament> findAllTournament() throws DataAccessException {
		return tournamentRepository.findAllTournament();
	}

	@Transactional
	public Collection<Tournament> findActiveTournaments() throws DataAccessException {
		return tournamentRepository.findActiveTournaments();
	}

	@Transactional()
	public Collection<Tournament> findTournamentByJudgeId(int judgeId) throws DataAccessException {
		return tournamentRepository.findTournamentByJudgeId(judgeId);
	}

	public Collection<Tournament> findEndedTournaments() throws DataAccessException {

		List<Tournament> allTournaments = (List<Tournament>) this.tournamentRepository.findAllTournament();

		return allTournaments.stream()
				.filter(t -> t.getEndDate().isBefore(LocalDate.now()) && TournamentDontHaveRanking(t))
				.collect(Collectors.toList());
	}

	private Boolean TournamentDontHaveRanking(Tournament t) throws DataAccessException {

		List<Ranking> allRankings = this.rankingRepository.findAllRanking();

		return allRankings.stream().noneMatch(r -> r.getTournament().equals(t));
	}

}
