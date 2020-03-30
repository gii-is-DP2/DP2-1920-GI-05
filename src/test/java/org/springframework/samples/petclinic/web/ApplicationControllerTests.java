package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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
	
	@BeforeEach
	void setup() {
						
		Collection<Application> ownerApplications = this.applicationService.findApplicationsByOwnerId(TEST_OWNER_ID);
		
		given(this.applicationService.findAllApplications()).willReturn(new ArrayList<>());
		given(this.applicationService.findApplicationsByOwnerId(TEST_OWNER_ID)).willReturn(ownerApplications);
	}

	@WithMockUser(value = "spring")
	@Test
	void testListAll() throws Exception {
		mockMvc.perform(get("/applications/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("applications")).andExpect(view().name("applications/list"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testListOwnerApplications() throws Exception {
		mockMvc.perform(get("/applications/{ownerId}/list", TEST_OWNER_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("applications")).andExpect(view().name("applications/list"));
	}


}
