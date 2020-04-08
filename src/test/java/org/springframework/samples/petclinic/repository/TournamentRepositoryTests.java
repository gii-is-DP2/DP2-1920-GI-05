package org.springframework.samples.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TournamentRepositoryTests {

	@Autowired
	TournamentRepository tournamentRepository;

	// Repository test: Return a tournament that already passes Negative Case
	@Test
	public void shouldNotReturnOldTournament() throws Exception {
				
		Collection<Tournament> activeTournaments = this.tournamentRepository.findActiveTournaments();
		
		Tournament t1 = this.tournamentRepository.findById(1);
		
		assertThat(activeTournaments.size()).isEqualTo(3);
		assertThat(!activeTournaments.contains(t1));
	}

}
