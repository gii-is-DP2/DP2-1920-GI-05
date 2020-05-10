package org.springframework.samples.petclinic.application;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class ApplicationControllerE2ETests {

	private static final int TEST_TOURNAMENT_ID = 6;
	private static final int TEST_APPLICATION_ID = 3;

	@Autowired
	private MockMvc mockMvc;	
	
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testListAll() throws Exception {
		mockMvc.perform(get("/applications/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("applications")).andExpect(view().name("applications/list"));
	}
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testListOwnerApplications() throws Exception {
		mockMvc.perform(get("/applications/list_mine")).andExpect(status().isOk())
				.andExpect(model().attributeExists("applications")).andExpect(view().name("applications/list"));
	}
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testGetNewApplication() throws Exception {
		mockMvc.perform(get("/applications/{tournamentId}/new", TEST_TOURNAMENT_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("applicationPOJO"))
				.andExpect(view().name("applications/createApplicationForm"));
	}
	
	// Post Create Fields Positive Case It does not work on Travis CI
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testProcessNewApplication() throws Exception {					
		mockMvc.perform(post("/applications/{tournamentId}/new", TEST_TOURNAMENT_ID).with(csrf())
				.param("pet", "Leo")
				.param("creditCard", "352571631239294"))				
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/applications/list_mine"));
	}
	
	// Post Create Fields Negative Case: wrong credit card
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testShouldNotProcessApplicationCreditCard() throws Exception {				

		mockMvc.perform(post("/applications/{tournamentId}/new", TEST_TOURNAMENT_ID).with(csrf())
				.param("pet", "Leo")
				.param("creditCard", "123"))
				.andExpect(model().attributeHasErrors("applicationPOJO"))
				.andExpect(status().isOk()).andExpect(view().name("applications/createApplicationForm"));
	}
	
	// Post Create Fields Positive Case
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testProcessUpdateApplication() throws Exception {
				mockMvc.perform(post("/applications/{applicationId}/edit", TEST_APPLICATION_ID).with(csrf())
				.param("owner", "Davis")
				.param("tournament", "Lovebirds speed contest  2020")
				.param("pet", "Jewel")		
				.param("status", "ACCEPTED")
				.param("creditCard", "352571631239294"))			
				//.andExpect(model().attributeHasNoErrors("application"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/applications/all"));
	}
	
	// Post Create Fields Negative Case: Invalid status
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testUpdateApplicationWrongStatus() throws Exception {
				mockMvc.perform(post("/applications/{applicationId}/edit", TEST_APPLICATION_ID).with(csrf())
				.param("owner", "Davis")
				.param("tournament", "Lovebirds speed contest  2020")
				.param("pet", "Jewel")		
				.param("status", "Negative")
				.param("creditCard", "352571631239294"))			
				.andExpect(model().attributeHasErrors("application"))
				.andExpect(model().attributeHasFieldErrors("application", "status"))
				.andExpect(status().isOk()).andExpect(view().name("applications/updateApplicationForm"));
	}
	
	// Post Create Fields Negative Case: Blank Pet, Tournament and Owner
	@WithMockUser(username="admin1",authorities= {"admin"})
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
