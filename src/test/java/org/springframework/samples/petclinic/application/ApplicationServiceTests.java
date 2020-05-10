package org.springframework.samples.petclinic.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicateApplicationException;
import org.springframework.samples.petclinic.service.exceptions.DuplicateCategoryNameException;
import org.springframework.samples.petclinic.service.exceptions.InactiveTournamentException;
import org.springframework.samples.petclinic.service.exceptions.InvalidPetTypeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ApplicationServiceTests {

	@Autowired
	protected ApplicationService applicationService;
	
	@Autowired
	protected OwnerService ownerService;
	
	@Autowired
	protected PetService petService;
	
	@Autowired
	protected TournamentService  tournamentService;
	
	
	// Service test: List all applications
	@Test
	void shouldFindAllApplications() {
		Collection<Application> applications = this.applicationService.findAllApplications();
		assertThat(applications.size()).isEqualTo(6);
	}
	
	//  Service test: List applications by owner
	@Test
	void shouldFindOwnerApplications() {
		Collection<Application> ownerApplications = this.applicationService.findApplicationsByOwnerId(1);
		assertThat(ownerApplications.size()).isEqualTo(2);
	}
	
    // Service test: Create new Applications Postive Case
	@Test
	void shouldCreateNewApplications() throws DataAccessException, DuplicateApplicationException, InvalidPetTypeException, InactiveTournamentException {
		
		Owner owner = this.ownerService.findOwnerById(6);
		Tournament tournament = this.tournamentService.findTournamentById(5);
		Pet pet = this.petService.findPetById(11);
		
		
		Application application = new Application();
		
		application.setCreditCard("4065972557141631");
		application.setMoment(LocalDate.of(2020, 10,  01));
		application.setOwner(owner);
		application.setStatus("PENDING");
		application.setTournament(tournament);
		application.setPet(pet);
		
		this.applicationService.saveApplication(application);
		Collection<Application> apps = this.applicationService.findAllApplications();
		assertThat(apps.size()).isEqualTo(7);
	}
	

	  // Service test: Create new Applications Negative Case: 
	 //Application when other application with same owner and tournament exists
	
	@Test
	@Transactional
	public void shouldThrowExceptionInsertingApplicationWithTheSameOwnerTournament() throws DataAccessException, DuplicateApplicationException, InvalidPetTypeException, InactiveTournamentException {		

		Application anotherApplication = new Application();	
		
		Owner owner = this.ownerService.findOwnerById(1); //  owner1
		Tournament tournament = this.tournamentService.findTournamentById(5); // hamster tournament
		Pet pet = this.petService.findPetById(2); // hamster from owner1
		
		anotherApplication.setId(6);
		anotherApplication.setCreditCard("4065972557141631");
		anotherApplication.setMoment(LocalDate.of(2020, 10, 01));
		anotherApplication.setOwner(owner);
		anotherApplication.setStatus("PENDING");
		anotherApplication.setTournament(tournament);
		anotherApplication.setPet(pet);
		
		Assertions.assertThrows(DuplicateApplicationException.class, () ->{ 
		applicationService.saveApplication(anotherApplication);});		
	}
	
	  // Service test: Create new Applications Negative Case: 
	 //Application when the pet has differente type from the tournament's pet type
	
	@Test
	@Transactional
	public void shouldThrowExceptionInsertingApplicationWithDifferentType() throws DataAccessException, DuplicateApplicationException, InvalidPetTypeException, InactiveTournamentException {		

		Application application = new Application();	
		
		Owner owner = this.ownerService.findOwnerById(4); //  owner3
		Tournament tournament = this.tournamentService.findTournamentById(10); // lizard tournament
		Pet pet = this.petService.findPetById(7); // snake from owner3
		
		application.setId(6);
		application.setCreditCard("4065972557141631");
		application.setMoment(LocalDate.of(2020, 10, 01));
		application.setOwner(owner);
		application.setStatus("PENDING");
		application.setTournament(tournament);
		application.setPet(pet);
		
		Assertions.assertThrows(InvalidPetTypeException.class, () ->{ 
		applicationService.saveApplication(application);});		
	}
	
	// Service test: Create new Applications Negative Case: 
   //Application to a inactive tournament
	
	@Test
	@Transactional
	public void shouldThrowExceptionInsertingApplicationToOldTournament() throws DataAccessException, DuplicateApplicationException, InvalidPetTypeException, InactiveTournamentException {		

		Application application = new Application();	
		
		Owner owner = this.ownerService.findOwnerById(7); //  owner7
		Tournament tournament = this.tournamentService.findTournamentById(2); // old dog tournament
		Pet pet = this.petService.findPetById(13); // dog from owner1
		
		application.setId(6);
		application.setCreditCard("4065972557141631");
		application.setMoment(LocalDate.of(2020, 10, 01));
		application.setOwner(owner);
		application.setStatus("PENDING");
		application.setTournament(tournament);
		application.setPet(pet);
		
		Assertions.assertThrows(InactiveTournamentException.class, () ->{ 
		applicationService.saveApplication(application);});		
	}
	
	
	
    // Service test: Update applications Postive Case
	@Test
	void shouldUpdateApplications() throws DataAccessException, InactiveTournamentException {
					
		Application application = this.applicationService.findApplicationById(4);		
		application.setStatus("REJECTED");
		
		this.applicationService.updateApplication(application);
		Collection<Application> apps = this.applicationService.findAllApplications();
		assertThat(apps.size()).isEqualTo(6);
	}
	
    // Service test: Update applications Negative Case: Inactive tournament
	@Test
	void shouldNotUpdateApplications() throws DataAccessException, InactiveTournamentException {
					
		Application application = this.applicationService.findApplicationById(1);		
		application.setStatus("REJECTED");
		
		Assertions.assertThrows(InactiveTournamentException.class, () ->{ 
		applicationService.updateApplication(application);});		
		
		Collection<Application> apps = this.applicationService.findAllApplications();
		assertThat(apps.size()).isEqualTo(6);
	}
	
}
