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
	
	@Test
	void shouldFindAllFields() {
		Collection<Application> applications = this.applicationService.findAllApplications();
		assertThat(applications.size()).isEqualTo(5);
	}
	
	@Test
	void shouldFindNewApplications() throws DataAccessException, DuplicateFieldNameException {
		
		Owner owner;
		Tournament tournament;
		Pet pet;
		
		owner = new Owner();
		owner.setFirstName("John Doe");
		
		tournament = new Tournament();
		tournament.setName("Quarentine's Tournament");
		
		pet = new Pet();
		pet.setName("Goomba");;	
		
		Application app = new Application();
		
		app.setCreditCard("4065972557141631");
		app.setMoment(LocalDate.of(2000, 10, 12));
		app.setOwner(owner);
		app.setStatus("");
		app.setTournament(tournament);
		app.setPet(pet);
		
		this.applicationService.saveApplication(app);
		Collection<Application> apps = this.applicationService.findAllApplications();
		assertThat(apps.size()).isEqualTo(6);
	}
}
