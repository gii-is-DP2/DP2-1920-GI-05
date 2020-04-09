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
import java.time.LocalDate;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Money;
import org.springframework.samples.petclinic.service.exceptions.DuplicateTournamentNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    		tournament = new Tournament();
    		money = new Money();
    		
    		category = this.categoryService.findCategoryById(1);
    		petType = this.petService.findPetTypes().iterator().next();
    		field = this.fieldService.findFieldById(1);
    		judge = this.judgeService.findJudgeById(1);
    		    		

    		money.setAmount(100.00);
    		money.setCurrency("$");   		
    		tournament.setApplyDate(LocalDate.of(2020, 10, 12));
    		tournament.setCategory(category);
    		tournament.setEndDate(LocalDate.of(2020, 12, 12));
    		tournament.setLocation("Seville");
    		tournament.setPetType(petType);
    		tournament.setPrize(money);
    		tournament.setName("Sample tournament");
    		tournament.setStartDate(LocalDate.of(2020, 12, 10));
  

    	}   

    // Find all Tournaments Postive Case
	@Test
	void shouldFindAllTournaments() {
		Collection<Tournament> tournaments = this.tournamentService.findAllTournament();
		assertThat(tournaments.size()).isEqualTo(6);
	}

	// Find active Tournaments Positive Case
	@Test
	void shouldFindActiveTournaments() {
		Collection<Tournament> tournaments = this.tournamentService.findActiveTournaments();
		assertThat(tournaments.size()).isEqualTo(3);
	}
	
	// Find all tournaments by Judge ID Positive case
	@Test
	void shouldFindAllTournamentsByJudgeId() {
		Collection<Tournament> tournaments = this.tournamentService.findTournamentByJudgeId(1);
		assertThat(tournaments.size()).isEqualTo(5);
	}

	
	// Save Tournament Postive Case
	@Test
	void shouldInsertdNewTournament() throws DataAccessException, DuplicateTournamentNameException {
				
		this.tournamentService.saveTournament(tournament);
		Collection<Tournament> tournaments = this.tournamentService.findAllTournament();
		assertThat(tournaments.size()).isEqualTo(7);
	}
	
	// Save Tournament Negative Case: Duplicated Name
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
	
	// Edit Tournament Postive Case: Insert new Field
	@Test
	void shouldUpdateFieldTournament() throws DataAccessException, DuplicateTournamentNameException {
		tournament.setField(field);		
		this.tournamentService.saveTournament(tournament);
		Collection<Tournament> tournaments = this.tournamentService.findAllTournament();
		assertThat(tournaments.size()).isEqualTo(7);
		assertThat(tournament.getField()).isEqualTo(field);
	}
	
	// Edit Tournament Postive Case: Insert new Judge
	@Test
	void shouldUpdateJudgeTournament() throws DataAccessException, DuplicateTournamentNameException {
		tournament.setJudge(judge);		
		
		this.tournamentService.saveTournament(tournament);
		Collection<Tournament> tournaments = this.tournamentService.findAllTournament();
		assertThat(tournaments.size()).isEqualTo(7);
		assertThat(tournament.getJudge()).isEqualTo(judge);
	}
	
	// Edit Tournament Negative Case: Updated Name to a name that is already taken
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
