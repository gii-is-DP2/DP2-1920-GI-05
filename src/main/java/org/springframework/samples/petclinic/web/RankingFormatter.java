package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Ranking;
import org.springframework.samples.petclinic.service.RankingService;
import org.springframework.stereotype.Component;

@Component
public class RankingFormatter implements Formatter<Ranking> {

	private final RankingService rankingService;

	@Autowired
	public RankingFormatter(RankingService rankingService) {
		this.rankingService = rankingService;
	}

	@Override
	public String print(Ranking ranking, Locale locale) {
		return ranking.getPodium().toString() + ranking.getTournament().toString();
	}

	@Override
	public Ranking parse(String text, Locale locale) throws ParseException {
		Collection<Ranking> findRankings = this.rankingService.findAll();
		for (Ranking ranking : findRankings) {
			if ((ranking.getPodium().toString() + ranking.getTournament().toString()).equals(text)) {
				return ranking;
			}
		}
		throw new ParseException("Ranking not found: " + text, 0);
	}

}
