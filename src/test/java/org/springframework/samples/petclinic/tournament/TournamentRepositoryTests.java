package org.springframework.samples.petclinic.tournament;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.repository.TournamentRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TournamentRepositoryTests {

	@Autowired
	TournamentRepository tournamentRepository;

	// Repository test: Return only active tournaments
	@Test
	public void shouldReturnActiveTournaments() throws Exception {
				
		Collection<Tournament> activeTournaments = this.tournamentRepository.findActiveTournaments();
		
		Tournament t1 = this.tournamentRepository.findById(1);
		
		assertThat(activeTournaments.size()).isEqualTo(8);
		assertThat(!activeTournaments.contains(t1));
	}
	
	// Repository test: Return tournaments by judge id
	@Test
	public void shouldReturnJudgeTournaments() throws Exception {
				
		Collection<Tournament> judgeTournaments = this.tournamentRepository.findTournamentByJudgeId(1);
		
		Tournament t1 = this.tournamentRepository.findById(1);
		
		assertThat(judgeTournaments.size()).isEqualTo(2);
		assertThat(judgeTournaments.contains(t1));
	}

}
