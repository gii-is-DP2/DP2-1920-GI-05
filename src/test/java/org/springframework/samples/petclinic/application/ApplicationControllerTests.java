package org.springframework.samples.petclinic.application;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.web.ApplicationController;
import org.springframework.samples.petclinic.web.OwnerFormatter;
import org.springframework.samples.petclinic.web.PetFormatter;
import org.springframework.samples.petclinic.web.PetTypeFormatter;
import org.springframework.samples.petclinic.web.TournamentFormatter;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = ApplicationController.class, 
includeFilters = @ComponentScan.Filter(value = {PetFormatter.class, TournamentFormatter.class, OwnerFormatter.class }, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), 
excludeAutoConfiguration = SecurityConfiguration.class)
class ApplicationControllerTests {

	private static final int TEST_OWNER_ID = 1;
	private static final int TEST_PET_ID = 2;
	private static final int TEST_TOURNAMENT_ID = 6;
	private static final int TEST_APPLICATION_ID = 1;

	@Autowired
	private ApplicationController applicationController;

	@MockBean
	private ApplicationService applicationService;

	@MockBean
	private PetService petService;

	@MockBean
	private OwnerService ownerService;

	@MockBean
	private TournamentService tournamentService;

	@Autowired
	private MockMvc mockMvc;

	private Owner george;
	
	private Tournament tournament;


	@BeforeEach
	void setup() {

		george = new Owner();
		george.setId(TEST_OWNER_ID);
		george.setFirstName("George");
		george.setLastName("Franklin");
		george.setAddress("110 W. Liberty St.");
		george.setCity("Madison");
		george.setTelephone("6085551023");

		Collection<Application> ownerApplications = this.applicationService.findApplicationsByOwnerId(TEST_OWNER_ID);

		//Cat tournament
		tournament = new Tournament();
		tournament.setId(11);
		tournament.setName("Cats obedience tournament 2020");
		given(this.tournamentService.findTournamentById(TEST_TOURNAMENT_ID)).willReturn(tournament);
		given(this.tournamentService.findAllTournament()).willReturn(Lists.newArrayList(tournament));
		
		
		given(this.applicationService.findAllApplications()).willReturn(new ArrayList<>());
		given(this.applicationService.findApplicationsByOwnerId(TEST_OWNER_ID)).willReturn(ownerApplications);
		
				
		PetType cat = new PetType();
		cat.setId(1);
		cat.setName("cat");		
		
		Pet pet =  new Pet();
		pet.setId(1);
		pet.setName("Leo");
		pet.setType(cat);
		given(this.petService.findAllPets()).willReturn(Lists.newArrayList(pet));
		given(this.petService.findPetByOwnerId(TEST_OWNER_ID)).willReturn(Lists.newArrayList(pet));
		
		Owner owner = new Owner();
		owner.setLastName("Michael");
		
		given(this.ownerService.findAllOwners()).willReturn(Lists.newArrayList(owner));
		
		Application application = new Application();
		application.setMoment(LocalDate.now());
		application.setId(6);
		application.setCreditCard("352571631239294");
		application.setStatus("PENDING");
		application.setPet(pet);
		application.setTournament(tournament);
		application.setOwner(owner);
		given(this.applicationService.findApplicationById(TEST_APPLICATION_ID)).willReturn(application);		
		

	}

	// List applications Positive Case
	@WithMockUser(value = "spring")
	@Test
	void testListAll() throws Exception {
		mockMvc.perform(get("/applications/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("applications")).andExpect(view().name("applications/list"));
	}

	// List applications by Owner Positive Case
	@WithMockUser(value = "spring")
	@Test
	void testListOwnerApplications() throws Exception {
		given(this.ownerService.findOwnerByUserName()).willReturn(george);
		mockMvc.perform(get("/applications/list_mine")).andExpect(status().isOk())
				.andExpect(model().attributeExists("applications")).andExpect(view().name("applications/list"));
	}

	// List applications by Owner Negativo Case when a owner is not authenticated
	@WithMockUser(value = "spring")
	@Test
	void testListOwnerApplicationsException() throws Exception {
		mockMvc.perform(get("/applications/list_mine")).andExpect(status().isOk()).andExpect(view().name("exception"));
	}

	// Create applications

	// Get Create Fields Positive Case
	@WithMockUser(value = "spring")
	@Test
	void testGetNewApplication() throws Exception {
		given(this.ownerService.findOwnerByUserName()).willReturn(george);
		mockMvc.perform(get("/applications/{tournamentId}/new", TEST_TOURNAMENT_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("applicationPOJO"))
				.andExpect(view().name("applications/createApplicationForm"));
	}

	// Post Create Fields Positive Case
	@WithMockUser(value = "spring")
	@Test
	void testProcessNewApplication() throws Exception {
					
		given(this.ownerService.findOwnerByUserName()).willReturn(george);		
		mockMvc.perform(post("/applications/{tournamentId}/new", TEST_TOURNAMENT_ID).with(csrf())
				.param("pet", "Leo")
				.param("creditCard", "352571631239294"))				
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/applications/list_mine"));
	}
	
	// Post Create Fields Negative Case: wrong credit card
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotProcessApplicationCreditCard() throws Exception {					
		given(this.ownerService.findOwnerByUserName()).willReturn(george);		
		mockMvc.perform(post("/applications/{tournamentId}/new", TEST_TOURNAMENT_ID).with(csrf())
				.param("pet", "Leo")
				.param("creditCard", "123"))
				.andExpect(model().attributeHasErrors("applicationPOJO"))
				.andExpect(status().isOk()).andExpect(view().name("applications/createApplicationForm"));
	}
	
	// Post Create Fields Positive Case
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateApplication() throws Exception {
				mockMvc.perform(post("/applications/{applicationId}/edit", TEST_APPLICATION_ID).with(csrf())
				.param("owner", "Michael")
				.param("tournament", "Cats obedience tournament 2020")
				.param("pet", "Leo")		
				.param("status", "ACCEPTED")
				.param("creditCard", "352571631239294"))			
				//.andExpect(model().attributeHasNoErrors("application"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/applications/all"));
	}
	
	// Post Create Fields Negative Case: Invalid status
	@WithMockUser(value = "spring")
	@Test
	void testUpdateApplicationWrongStatus() throws Exception {
				mockMvc.perform(post("/applications/{applicationId}/edit", TEST_APPLICATION_ID).with(csrf())
				.param("owner", "Michael")
				.param("tournament", "Cats obedience tournament 2020")
				.param("pet", "Leo")		
				.param("status", "Negative")
				.param("creditCard", "352571631239294"))			
				.andExpect(model().attributeHasErrors("application"))
				.andExpect(model().attributeHasFieldErrors("application", "status"))
				.andExpect(status().isOk()).andExpect(view().name("applications/updateApplicationForm"));
	}
	
	// Post Create Fields Negative Case: Blank Pet, Tournament and Owner
	@WithMockUser(value = "spring")
	@Test
	void testUpdateApplicationBlank() throws Exception {
				mockMvc.perform(post("/applications/{applicationId}/edit", TEST_APPLICATION_ID).with(csrf())
				.param("owner", "")
				.param("tournament", "")
				.param("pet", "")		
				.param("status", "ACCEPTED")
				.param("creditCard", "352571631239294"))
				.andExpect(model().attributeHasErrors("application"))
				.andExpect(model().attributeHasFieldErrors("application", "pet"))
				.andExpect(model().attributeHasFieldErrors("application", "tournament"))
				.andExpect(model().attributeHasFieldErrors("application", "owner"))
				.andExpect(status().isOk()).andExpect(view().name("applications/updateApplicationForm"));
	}
	
	
	

}
