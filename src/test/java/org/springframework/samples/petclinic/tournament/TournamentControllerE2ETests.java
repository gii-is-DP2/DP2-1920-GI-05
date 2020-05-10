package org.springframework.samples.petclinic.tournament;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.samples.petclinic.service.GuideService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicateTournamentNameException;
import org.springframework.samples.petclinic.web.CategoryFormatter;
import org.springframework.samples.petclinic.web.FieldFormatter;
import org.springframework.samples.petclinic.web.JudgeFormatter;
import org.springframework.samples.petclinic.web.OwnerController;
import org.springframework.samples.petclinic.web.PetTypeFormatter;
import org.springframework.samples.petclinic.web.TournamentController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class TournamentControllerE2ETests {

	private static final int TEST_TOURNAMENT_ID = 1;
	
	private static final int TEST_JUDGE_ID = 1;

	@Autowired
	private MockMvc mockMvc;
	

	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test	
	void testListAll() throws Exception {
		mockMvc.perform(get("/tournaments/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("tournaments")).andExpect(view().name("tournaments/list"));
	}

	@WithMockUser(username="guide1",authorities= {"admin"})
	@Test
	void testListActive() throws Exception {
		mockMvc.perform(get("/tournaments/active")).andExpect(status().isOk())
				.andExpect(model().attributeExists("tournaments")).andExpect(view().name("tournaments/list"));
	}

	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testGetNewTournaments() throws Exception {
		mockMvc.perform(get("/tournaments/new")).andExpect(status().isOk())
		.andExpect(model().attributeExists("tournament")).andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Positive case valid inputs
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testProcessTournamentFormSuccess() throws Exception {		
		mockMvc.perform(post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("petType", "Hamster")				
				.param("category","Agility")
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")
				.param("endDate", "2020/12/12")
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				//.andExpect(model().attributeHasNoErrors("tournament"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/tournaments/all"));
	}
	
	// Create tournament Negative Case: Missed name input
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentBlankName() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("location", "Seville")
				.param("petType", "Hamster")				
				.param("category","Agility")
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: missed location input
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentBlankLocation() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "")
				.param("petType", "Hamster")				
				.param("category","Agility")
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: missed category input
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentBlankCategory() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("petType", "Hamster")
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: missed pet type input
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentBlankPetType() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: missed apply date input 
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentBlankApplyDate() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "Hamster")
				.param("startDate", "2020/12/11")
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: Apply date after star date
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentWrongApplyDate() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "Hamster")
				.param("applyDate", "2020/12/13")
				.param("startDate", "2020/12/11")
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	
	// Create Tournament Negative Case: start date after end date
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentWrongStartDate() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "Hamster")
				.param("applyDate", "2020/12/10")	
				.param("startDate", "2020/12/13")
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: end date before start date
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentWrongEndDate() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "Hamster")
				.param("applyDate", "2020/12/11")	
				.param("startDate", "2020/12/12")
				.param("endDate", "2020/12/10")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}	
	
	// Create Tournament Negative Case: missed start date input
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentBlankStartDate() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "Hamster")
				.param("applyDate", "2020/12/10")				
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: missed end date input
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentBlankEndDate() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "Hamster")		
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")	
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: missed prize amount input
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentBlankAmount() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "Hamster")		
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")	
				.param("endDate", "2020/12/12")				
				.param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: invalid amount input
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentWrongAmount1() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "Hamster")		
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")	
				.param("endDate", "2020/12/12")			
				.param("prize.amount", "500.001").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: invalid amount input
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentWrongAmount2() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "Hamster")		
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")	
				.param("endDate", "2020/12/12")			
				.param("prize.amount", "50000000.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: missed prize currency input
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentBlankCurrency() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "Hamster")		
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")	
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", ""))		
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: invalid currency input
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testShouldNotCreateTournamentWrongCurrency() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "Hamster")		
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")	
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "dolar"))		
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Edit tournament Init Test 
		@WithMockUser(username="admin1",authorities= {"admin"})
	  @Test 
	  void testUpdateTournaments() throws Exception {
	  mockMvc.perform(get("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)).andExpect(status().
	  isOk())
	  .andExpect(model().attributeExists("tournament")).andExpect(view().name(
	  "tournaments/createOrUpdateTournamentForm")); 
	  }
	  
	// Edit user Positive Case: all valid inputs 
		@WithMockUser(username="admin1",authorities= {"admin"})
	  @Test 
	  void testUpdateTournamentsSuccess() throws Exception {
	  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
	    .with(csrf())
		.param("name", "Betty tournament")
		.param("location", "Seville")
		.param("category","Agility")
		.param("petType", "Hamster")		
		.param("applyDate", "2020/12/10")
		.param("startDate", "2020/12/11")	
		.param("endDate", "2020/12/12")				
		.param("prize.amount", "500.00").param("prize.currency", "EUR")
	  
	  	//Updating judge and field
	  	.param("field", "Map 1")
	  	.param("judge", "Dacon"))
	  	.andExpect(status().is3xxRedirection())	  
	  	.andExpect(view().name("redirect:/tournaments/all")); 
	  }
	  
		// Edit user Negative Case: Blank location
	@WithMockUser(username="admin1",authorities= {"admin"})
	  @Test 
	  void testsShoulNotUpdateTournaments() throws Exception {
	  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
	    .with(csrf())		
	    .param("name", "Betty tornament")
		.param("location", "")
		.param("category","Agility")
		.param("petType", "Hamster")		
		.param("applyDate", "2020/12/10")
		.param("startDate", "2020/12/11")	
		.param("endDate", "2020/12/12")				
		.param("prize.amount", "500.00").param("prize.currency", "EUR")
	  
	  	//Updating judge and field
	  	.param("field", "Map 1")
	  	.param("judge", "Dacon"))
		.andExpect(model().attributeHasErrors("tournament"))
		.andExpect(status().isOk())
		.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	  }
	
	// Edit user Negative Case: Blank Name
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments2() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())		
		    .param("name", "")
			.param("location", "Seville")
			.param("category","Agility")
			.param("petType", "Hamster")		
			.param("applyDate", "2020/12/10")
			.param("startDate", "2020/12/11")	
			.param("endDate", "2020/12/12")				
			.param("prize.amount", "500.00").param("prize.currency", "EUR")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
		  }
		
		// Edit user Negative Case: missed input category
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments3() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())
			.param("name", "Betty tournament")
			.param("location", "Seville")
			.param("petType", "Hamster")		
			.param("applyDate", "2020/12/10")
			.param("startDate", "2020/12/11")	
			.param("endDate", "2020/12/12")				
			.param("prize.amount", "500.00").param("prize.currency", "EUR")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
		  }
		
		// Edit user Negative Case: missed input petType
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments4() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())
			.param("name", "Betty tournament")
			.param("location", "Seville")
			.param("category","Agility")		
			.param("applyDate", "2020/12/10")
			.param("startDate", "2020/12/11")	
			.param("endDate", "2020/12/12")				
			.param("prize.amount", "500.00").param("prize.currency", "EUR")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
		  }
		
		// Edit user Negative Case: missed input ApplyDate
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments5() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())
			.param("name", "Betty tournament")
			.param("location", "Seville")
			.param("category","Agility")
			.param("petType", "Hamster")		
			.param("startDate", "2020/12/11")	
			.param("endDate", "2020/12/12")				
			.param("prize.amount", "500.00").param("prize.currency", "EUR")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm")); 
		  }
		
		// Edit user Negative Case: missed input StartDate
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments6() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())
			.param("name", "Betty tournament")
			.param("location", "Seville")
			.param("category","Agility")
			.param("petType", "Hamster")		
			.param("applyDate", "2020/12/10")
			.param("startDate", "2020/12/11")			
			.param("prize.amount", "500.00").param("prize.currency", "EUR")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
		  }
		
		// Edit user Negative Case: missed input EndDate
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments7() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())
			.param("name", "Betty tournament")
			.param("location", "Seville")
			.param("category","Agility")
			.param("petType", "Hamster")		
			.param("applyDate", "2020/12/10")
			.param("startDate", "2020/12/11")					
			.param("prize.amount", "500.00").param("prize.currency", "EUR")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
		  }
		
		// Edit user Negative Case: missed input prize ammount
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments8() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())
			.param("name", "Betty tournament")
			.param("location", "Seville")
			.param("category","Agility")
			.param("petType", "Hamster")		
			.param("applyDate", "2020/12/10")
			.param("startDate", "2020/12/11")	
			.param("endDate", "2020/12/12")				
			.param("prize.currency", "EUR")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
		  }
		
		// Create Tournament Negative Case: Apply date after star date
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments9() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())
			.param("name", "Betty tournament")
			.param("location", "Seville")
			.param("petType", "Hamster")		
			.param("applyDate", "2020/12/13")
			.param("startDate", "2020/12/11")	
			.param("endDate", "2020/12/12")				
			.param("prize.amount", "500.00").param("prize.currency", "EUR")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
		  }
		
		// Create Tournament Negative Case: start date after end date
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments10() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())
			.param("name", "Betty tournament")
			.param("location", "Seville")
			.param("petType", "Hamster")		
			.param("applyDate", "2020/12/10")
			.param("startDate", "2020/12/13")	
			.param("endDate", "2020/12/12")				
			.param("prize.amount", "500.00").param("prize.currency", "EUR")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
		  }

		// Create Tournament Negative Case: end date before start date
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments11() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())
			.param("name", "Betty tournament")
			.param("location", "Seville")
			.param("petType", "Hamster")		
			.param("applyDate", "2020/12/10")
			.param("startDate", "2020/12/11")	
			.param("endDate", "2020/12/19")				
			.param("prize.amount", "500.00").param("prize.currency", "EUR")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
		  }
		
		// Create Tournament Negative Case: invalid prize amount input
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments12() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())
			.param("name", "Betty tournament")
			.param("location", "Seville")
			.param("petType", "Hamster")		
			.param("applyDate", "2020/12/10")
			.param("startDate", "2020/12/11")	
			.param("endDate", "2020/12/12")				
			.param("prize.amount", "500.000001").param("prize.currency", "EUR")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
		  }
		
		// Create Tournament Negative Case: invalid prize amount input
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments13() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())
			.param("name", "Betty tournament")
			.param("location", "Seville")
			.param("petType", "Hamster")		
			.param("applyDate", "2020/12/10")
			.param("startDate", "2020/12/11")	
			.param("endDate", "2020/12/12")				
			.param("prize.amount", "50000000000000.00").param("prize.currency", "EUR")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
		  }
		
		// Create Tournament Negative Case: blank prize currency input
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments14() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())
			.param("name", "Betty tournament")
			.param("location", "Seville")
			.param("petType", "Hamster")		
			.param("applyDate", "2020/12/10")
			.param("startDate", "2020/12/11")	
			.param("endDate", "2020/12/12")				
			.param("prize.amount", "500.00").param("prize.currency", "")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
		  }
		
		// Create Tournament Negative Case: wrong prize currency input
		@WithMockUser(username="admin1",authorities= {"admin"})
		  @Test 
		  void testsShoulNotUpdateTournaments15() throws Exception {
		  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
		    .with(csrf())
			.param("name", "Betty tournament")
			.param("location", "Seville")
			.param("petType", "Hamster")		
			.param("applyDate", "2020/12/10")
			.param("startDate", "2020/12/11")	
			.param("endDate", "2020/12/12")				
			.param("prize.amount", "500.00").param("prize.currency", "dolar")
		  
		  	//Updating judge and field
		  	.param("field", "Map 1")
		  	.param("judge", "Dacon"))
			.andExpect(model().attributeHasErrors("tournament"))
			.andExpect(status().isOk())
			.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
		  }		
		

	  




	

	
	

	 
}
