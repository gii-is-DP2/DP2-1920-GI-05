/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Money;
import org.springframework.samples.petclinic.service.exceptions.DuplicateCategoryNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicateFieldNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicateTournamentNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * TournamentServiceTest#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class TournamentServiceTest { 
	
    @Autowired
	protected TournamentService tournamentService;
    
	@Autowired
	protected PetService petService;
	
	@Autowired
	protected CategoryService categoryService;
	
	@Autowired
	protected JudgeService judgeService;
	
	@Autowired
	protected FieldService fieldService;
                
        
    	private Money money;
    	private Category category;
    	private PetType petType;
    	private Tournament tournament;
    	private Field field;
    	private Judge judge;

    	@BeforeEach
    	void setup() {

    		category = this.categoryService.findCategoryById(1);
    		petType = this.petService.findPetTypes().iterator().next();
    		field = this.fieldService.findFieldById(1);
    		judge = this.judgeService.findJudgeById(1);
    		
    		
    		money = new Money();
    		money.setAmount(100.00);
    		money.setCurrency("$");

    		
    		tournament = new Tournament();

    		tournament.setApplyDate(LocalDate.of(2020, 10, 12));

    		tournament.setCategory(category);
    		tournament.setEndDate(LocalDate.of(2020, 12, 12));
    		// tournament.setField();
    		// tournament.setJugde();
    		tournament.setLocation("Seville");

    		tournament.setPetType(petType);

    		tournament.setPrize(money);
    		tournament.setName("Sample tournament");
    		tournament.setStartDate(LocalDate.of(2020, 12, 10));
  

    	}   

	@Test
	void shouldFindAllTournaments() {
		Collection<Tournament> tournaments = this.tournamentService.findAllTournament();
		assertThat(tournaments.size()).isEqualTo(4);
	}

	@Test
	void shouldFindActiveTournaments() {
		Collection<Tournament> tournaments = this.tournamentService.findActiveTournaments();
		assertThat(tournaments.size()).isEqualTo(1);
	}
	
	@Test
	void shouldInsertdNewTournament() throws DataAccessException, DuplicateTournamentNameException {
				
		this.tournamentService.saveTournament(tournament);
		Collection<Tournament> tournaments = this.tournamentService.findAllTournament();
		assertThat(tournaments.size()).isEqualTo(5);
	}
	
	@Test
	@Transactional
	public void shouldThrowExceptionInsertingTournamentWithTheSameName() {		
		tournament.setName("Winbendoll tournament 3");
		try {
			tournamentService.saveTournament(tournament);		
		} catch (DuplicateTournamentNameException e) {
		
			e.printStackTrace();
		}
		
		Tournament anotherTournamentWithTheSameName = new Tournament();	
		anotherTournamentWithTheSameName.setName("Winbendoll tournament 3");
		Assertions.assertThrows(DuplicateTournamentNameException.class, () ->{tournamentService.saveTournament(anotherTournamentWithTheSameName);});		
	}
	
	@Test
	void shouldUpdateFieldTournament() throws DataAccessException, DuplicateTournamentNameException {
		tournament.setField(field);		
		this.tournamentService.saveTournament(tournament);
		Collection<Tournament> tournaments = this.tournamentService.findAllTournament();
		assertThat(tournaments.size()).isEqualTo(5);
		assertThat(tournament.getField()).isEqualTo(field);
	}
	
	@Test
	void shouldUpdateJudgeTournament() throws DataAccessException, DuplicateTournamentNameException {
		tournament.setJudge(judge);		
		
		this.tournamentService.saveTournament(tournament);
		Collection<Tournament> tournaments = this.tournamentService.findAllTournament();
		assertThat(tournaments.size()).isEqualTo(5);
		assertThat(tournament.getJudge()).isEqualTo(judge);
	}
	
	@Test
	void shouldNotUpdateTournamentWithTheSameName() throws DataAccessException, DuplicateTournamentNameException {
		tournament.setField(field);		
		tournament.setJudge(judge);		
		this.tournamentService.saveTournament(tournament);
		try {
			tournamentService.saveTournament(tournament);		
		} catch (DuplicateTournamentNameException e) {
		
			e.printStackTrace();
		}
		
		Tournament anotherTournamentWithTheSameName = new Tournament();	
		anotherTournamentWithTheSameName.setName("Winbendoll tournament 3");
		Assertions.assertThrows(DuplicateTournamentNameException.class, () ->{tournamentService.saveTournament(anotherTournamentWithTheSameName);});		
	}


}
