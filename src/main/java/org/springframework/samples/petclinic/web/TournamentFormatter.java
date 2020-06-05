package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.stereotype.Component;

@Component
public class TournamentFormatter implements Formatter<Tournament> {

	private final TournamentService tournamentService;

	@Autowired
	public TournamentFormatter(TournamentService tournamentService) {
		this.tournamentService = tournamentService;
	}

	@Override
	public String print(Tournament tournament, Locale locale) {
		return tournament.getName() ;
	}

	@Override
	public Tournament parse(String text, Locale locale) throws ParseException {
		Collection<Tournament> findTournaments = this.tournamentService.findAllTournament();
		for (Tournament tournament : findTournaments) {
			if (tournament.getName().equals(text)) {
				return tournament;
			}
		}
		throw new ParseException("Tournament not found: " + text, 0);
	}

}
