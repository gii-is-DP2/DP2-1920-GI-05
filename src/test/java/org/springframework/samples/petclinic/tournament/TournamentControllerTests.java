package org.springframework.samples.petclinic.tournament;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.samples.petclinic.service.GuideService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RankingService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.web.CategoryFormatter;
import org.springframework.samples.petclinic.web.FieldFormatter;
import org.springframework.samples.petclinic.web.JudgeFormatter;
import org.springframework.samples.petclinic.web.OwnerController;
import org.springframework.samples.petclinic.web.PetTypeFormatter;
import org.springframework.samples.petclinic.web.TournamentController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@WebMvcTest(controllers = TournamentController.class, 
		includeFilters = @ComponentScan.Filter(value = {
		CategoryFormatter.class, FieldFormatter.class, JudgeFormatter.class,
		PetTypeFormatter.class }, type = FilterType.ASSIGNABLE_TYPE), 
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), 
		excludeAutoConfiguration = SecurityConfiguration.class)
class TournamentControllerTests {

	private static final int TEST_TOURNAMENT_ID = 1;
	
	private static final int TEST_JUDGE_ID = 1;


	@MockBean
	private PetService petService;

	@MockBean
	private FieldService fieldService;

	@MockBean
	private CategoryService categoryService;

	@MockBean
	private OwnerService ownerService;

	@MockBean
	private GuideService guideService;

	@MockBean
	private JudgeService judgeService;

	@MockBean
	private TournamentService tournamentService;
	
	@MockBean
	private RankingService rankingService;
	
	@MockBean
	private ApplicationService applicationService;

	@Autowired
	private MockMvc mockMvc;
	


	@BeforeEach
	void setup() {
						
		PetType cat = new PetType();
		cat.setId(3);
		cat.setName("hamster");
		given(this.petService.findPetTypes()).willReturn(Lists.newArrayList(cat));
		
		Field field = new Field();
		field.setId(3);
		field.setName("Ice");
		given(this.fieldService.findAllFields()).willReturn(Lists.newArrayList(field));

		Category category = new Category();
		category.setId(1);
	    category.setName("Agility");
	    given(this.categoryService.findAllCategories()).willReturn(Lists.newArrayList(category));
   	    
		Judge judge = new Judge();
		judge.setId(3);
		judge.setLastName("Peter");
		given(this.judgeService.findAllJudges()).willReturn(Lists.newArrayList(judge));
		

		Tournament tournament = new Tournament();
		tournament.setId(10);
		given(this.tournamentService.findTournamentById(TEST_TOURNAMENT_ID)).willReturn(tournament);
		given(this.tournamentService.findTournamentByJudgeId(TEST_JUDGE_ID)).willReturn(Lists.newArrayList(tournament));
	}

	@WithMockUser(value = "spring")
	@Test	
	void testListAll() throws Exception {
		mockMvc.perform(get("/tournaments/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("tournaments")).andExpect(view().name("tournaments/list"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testListActive() throws Exception {
		mockMvc.perform(get("/tournaments/active")).andExpect(status().isOk())
				.andExpect(model().attributeExists("tournaments")).andExpect(view().name("tournaments/list"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testListEnded() throws Exception {
		mockMvc.perform(get("/tournaments/endedList")).andExpect(status().isOk())
				.andExpect(model().attributeExists("tournaments")).andExpect(view().name("tournaments/endedList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testGetNewTournaments() throws Exception {
		mockMvc.perform(get("/tournaments/new")).andExpect(status().isOk())
		.andExpect(model().attributeExists("tournament")).andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Positive case valid inputs
	@WithMockUser(value = "spring")
	@Test
	void testProcessTournamentFormSuccess() throws Exception {		
		mockMvc.perform(post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("petType", "hamster")				
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
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentBlankName() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("location", "Seville")
				.param("petType", "hamster")				
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
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentBlankLocation() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "")
				.param("petType", "hamster")				
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
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentBlankCategory() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("petType", "hamster")
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: missed pet type input
	@WithMockUser(value = "spring")
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
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentBlankApplyDate() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "hamster")
				.param("startDate", "2020/12/11")
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: Apply date after star date
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentWrongApplyDate() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "hamster")
				.param("applyDate", "2020/12/13")
				.param("startDate", "2020/12/11")
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	
	// Create Tournament Negative Case: start date after end date
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentWrongStartDate() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "hamster")
				.param("applyDate", "2020/12/10")	
				.param("startDate", "2020/12/13")
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: end date before start date
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentWrongEndDate() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "hamster")
				.param("applyDate", "2020/12/11")	
				.param("startDate", "2020/12/12")
				.param("endDate", "2020/12/10")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}	
	
	// Create Tournament Negative Case: missed start date input
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentBlankStartDate() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "hamster")
				.param("applyDate", "2020/12/10")				
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: missed end date input
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentBlankEndDate() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "hamster")		
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")	
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: missed prize amount input
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentBlankAmount() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "hamster")		
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")	
				.param("endDate", "2020/12/12")				
				.param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: invalid amount input
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentWrongAmount1() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "hamster")		
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")	
				.param("endDate", "2020/12/12")			
				.param("prize.amount", "500.001").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: invalid amount input
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentWrongAmount2() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "hamster")		
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")	
				.param("endDate", "2020/12/12")			
				.param("prize.amount", "50000000.00").param("prize.currency", "EUR"))
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: missed prize currency input
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentBlankCurrency() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "hamster")		
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")	
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", ""))		
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Create Tournament Negative Case: invalid currency input
	@WithMockUser(value = "spring")
	@Test
	void testShouldNotCreateTournamentWrongCurrency() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("name", "Betty tornament")
				.param("location", "Seville")
				.param("category","Agility")
				.param("petType", "hamster")		
				.param("applyDate", "2020/12/10")
				.param("startDate", "2020/12/11")	
				.param("endDate", "2020/12/12")				
				.param("prize.amount", "500.00").param("prize.currency", "dolar"))		
				.andExpect(model().attributeHasErrors("tournament"))
				.andExpect(status().isOk())
				.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	}
	
	// Edit tournament Init Test 
	  @WithMockUser(value = "spring")	  
	  @Test void testUpdateTournaments() throws Exception {
	  mockMvc.perform(get("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)).andExpect(status().
	  isOk())
	  .andExpect(model().attributeExists("tournament")).andExpect(view().name(
	  "tournaments/createOrUpdateTournamentForm")); 
	  }
	  
	// Edit user Positive Case: all valid inputs 
	  @WithMockUser(value = "spring", authorities = "admin")	  
	  @Test 
	  void testUpdateTournamentsSuccess() throws Exception {
	  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
	    .with(csrf())
		.param("name", "Betty tornament")
		.param("location", "Seville")
		.param("category","Agility")
		.param("petType", "hamster")		
		.param("applyDate", "2020/12/10")
		.param("startDate", "2020/12/11")	
		.param("endDate", "2020/12/12")				
		.param("prize.amount", "500.00").param("prize.currency", "EUR")
	  
	  	//Updating judge and field
	  	.param("field", "Ice")
	  	.param("judge", "Peter"))
	  	.andExpect(status().is3xxRedirection())	  
	  	.andExpect(view().name("redirect:/tournaments/all")); 
	  }
	  
		// Edit user Negative Case: Blank location
	  @WithMockUser(value = "spring")	  
	  @Test 
	  void testsShoulNotUpdateTournaments() throws Exception {
	  mockMvc.perform(post("/tournaments/{tournamentId}/edit", TEST_TOURNAMENT_ID)
	    .with(csrf())		
	    .param("name", "Betty tornament")
		.param("location", "")
		.param("category","Agility")
		.param("petType", "hamster")		
		.param("applyDate", "2020/12/10")
		.param("startDate", "2020/12/11")	
		.param("endDate", "2020/12/12")				
		.param("prize.amount", "500.00").param("prize.currency", "EUR")
	  
	  	//Updating judge and field
	  	.param("field", "Ice")
	  	.param("judge", "Peter"))
		.andExpect(model().attributeHasErrors("tournament"))
		.andExpect(status().isOk())
		.andExpect(view().name("tournaments/createOrUpdateTournamentForm"));
	  }
	  




	

	
	

	 
}
