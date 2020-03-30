package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.exceptions.DuplicateFieldNameException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ApplicationServiceTest {

	@Autowired
	protected ApplicationService applicationService;
	
	@Autowired
	protected OwnerService ownerService;
	
	@Autowired
	protected PetService petService;
	
	@Autowired
	protected TournamentService  tournamentService;
	
	@Test
	void shouldFindAllApplications() {
		Collection<Application> applications = this.applicationService.findAllApplications();
		assertThat(applications.size()).isEqualTo(5);
	}
	
	@Test
	void shouldFindOwnerApplications() {
		Collection<Application> ownerApplications = this.applicationService.findApplicationsByOwnerId(1);
		assertThat(ownerApplications.size()).isEqualTo(2);
	}
	
	@Test
	void shouldFindNewApplications() throws DataAccessException {
		
		Owner owner = this.ownerService.findOwnerById(2);
		Tournament tournament = this.tournamentService.findTournamentById(3);
		Pet pet = this.petService.findPetById(2);
		
		
		Application application = new Application();
		
		application.setCreditCard("4065972557141631");
		application.setMoment(LocalDate.of(2000, 10, 12));
		application.setOwner(owner);
		application.setStatus("PENDING");
		application.setTournament(tournament);
		application.setPet(pet);
		
		this.applicationService.saveApplication(application);
		Collection<Application> apps = this.applicationService.findAllApplications();
		assertThat(apps.size()).isEqualTo(6);
	}
}
