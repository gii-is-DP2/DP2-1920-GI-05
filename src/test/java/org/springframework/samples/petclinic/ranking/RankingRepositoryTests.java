package org.springframework.samples.petclinic.ranking;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Ranking;
import org.springframework.samples.petclinic.repository.RankingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class RankingRepositoryTests {

	@Autowired
	RankingRepository rankingRepository;
	
	@Test
	public void shouldReturnRankingsByTournament() throws Exception {
				
		List<Ranking> rankings = this.rankingRepository.findByTournamentId(2);
		
		assertThat(rankings.size()).isEqualTo(1);
	}
	
 	@Test
	public void shouldReturnAllRankings() throws Exception {
				
		List<Ranking> rankings = this.rankingRepository.findAllRanking();
		
		assertThat(rankings.size()).isEqualTo(1);
	}
	
	@Test
	public void shouldReturnRankingsById() throws Exception {
				
		Ranking ranking = this.rankingRepository.findById(1);
		
		assertThat(ranking.getId().equals(1));
	} 
	

}
