package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;

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
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@Nested
@WebMvcTest(controllers = ApplicationController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class ApplicationControllerTests {

	private static final int TEST_OWNER_ID = 1;
	
	@Autowired
	private ApplicationController applicationController;
	
	@MockBean
	private ApplicationService applicationService;
	
	@MockBean
	private PetService  petService;
	
	@MockBean
	private OwnerService  ownerService;
	
	@MockBean
	private TournamentService  tournamentService;
	
	@Autowired	
	private MockMvc mockMvc;	
	
	private Owner george;
	
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
		
			
		given(this.applicationService.findAllApplications()).willReturn(new ArrayList<>());
		given(this.applicationService.findApplicationsByOwnerId(TEST_OWNER_ID)).willReturn(ownerApplications);
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

	// List applications by Owner Negativo Case
	@WithMockUser(value = "spring")
	@Test
	void testListOwnerApplicationsException() throws Exception {
		mockMvc.perform(get("/applications/list_mine")).andExpect(status().isOk())
		.andExpect(view().name("exception"));
	}


}
