package org.springframework.samples.petclinic.ranking;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Ranking;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RankingService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicateTournamentNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
class RankingServiceTest { 
	
    @Autowired
	protected RankingService rankingService;
    
	@Autowired
	protected PetService petService;

	@Autowired
	protected TournamentService tournamentService;

    // Find all Rankings
	@Test
	@Transactional
	void shouldFindAllRankings() {
		Collection<Ranking> rankings = this.rankingService.findAll();
		assertThat(rankings.size()).isEqualTo(1);
	}
	
	// Find Ranking by Tournament ID 

	@Test
	@Transactional
	void shouldFindRakingByTournamentId() {
		Collection<Ranking> ranking = this.rankingService.findRankingByTournamentId(2);
		assertThat(ranking.size()).isEqualTo(1);
	}

	// Save Ranking / Make Ranking

	@Test
	@Transactional
	void shouldInsertdNewRanking() throws DataAccessException, DuplicateTournamentNameException {
				
		Ranking ranking = this.rankingService.makeRanking(this.tournamentService.findTournamentById(1));
		this.rankingService.saveRanking(ranking);
		Collection<Ranking> rankings = this.rankingService.findAll();
		assertThat(rankings.size()).isEqualTo(2);
	}
	
	// Get score from a pet in a ranking
	@Test
	@Transactional
	void testFindScoreByPetIdAndRanking() throws DataAccessException {
		
		Integer integer = this.rankingService.findScoreByPetIdAndRanking(this.rankingService.findRankingById(1), 3);
		assertThat(integer.equals(30));
	}
	

}
