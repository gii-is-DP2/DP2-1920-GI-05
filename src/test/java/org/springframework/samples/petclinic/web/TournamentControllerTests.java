package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.samples.petclinic.service.GuideService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@WebMvcTest(controllers = TournamentController.class, includeFilters = @ComponentScan.Filter(value = {
		CategoryFormatter.class, FieldFormatter.class, JudgeFormatter.class,
		PetTypeFormatter.class }, type = FilterType.ASSIGNABLE_TYPE), excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class TournamentControllerTests {

	private static final int TEST_TOURNAMENT_ID = 1;

	@Autowired
	private TournamentController tournamentController;

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

	@Autowired
	private MockMvc mockMvc;
	
	private Category category;

	@BeforeEach
	void setup() {
		
		PetType cat = new PetType();
		cat.setId(3);
		cat.setName("hamster");
		given(this.petService.findPetTypes()).willReturn(Lists.newArrayList(cat));
		// given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(new
		// Owner());
		// given(this.petService.findPetById(TEST_PET_ID)).willReturn(new Pet());

		Field field = new Field();
		field.setId(3);
		field.setName("Ice");
		given(this.fieldService.findAllFields()).willReturn(Lists.newArrayList(field));

		category = new Category();
		category.setId(1);
	    category.setName("Agility");
	    given(this.categoryService.findAllCategories()).willReturn(Lists.newArrayList(category));

		//Category category = new Category();
		//category.setId(1);
		//category.setName("Speed");
		//given(this.categoryService.findCategoryById(category.getId())).willReturn(new Category());
		//given(this.categoryService.findAllCategories()).willReturn(Lists.newArrayList(category));
	    
	    User user = new User();
	    user.setEnabled(true);
	    user.setUsername("peter");
	    user.setPassword("easy");
	    
		Judge judge = new Judge();
		judge.setId(3);
		judge.setUser(user);
		judge.setLastName("Peter");
		judge.setFirstName("Randal");
		judge.setAddress("Sevilla");
		judge.setCity("Tomares");
		judge.setTelephone("6666666");
		//given(this.judgeService.findJudgeById(judge.getId())).willReturn(new Judge());
		given(this.judgeService.findAllJudges()).willReturn(Lists.newArrayList(judge));

		Tournament tournament = new Tournament();
		tournament.setId(10);
		given(this.tournamentService.findTournamentById(tournament.getId())).willReturn(new Tournament());
	}

	/*
	 * 
	 * @WithMockUser(value = "spring")
	 * 
	 * @Test void testProcessCreationFormSuccess() throws Exception {
	 * mockMvc.perform(post("/tournaments/new").with(csrf()).param("name",
	 * "Betty").param("location", "Seville") //.param("petType",
	 * "hamster").param("field", "Ice") .param("applyDate",
	 * "2021/01/01").param("startDate", "2021/01/02").param("endDate", "2021/01/03")
	 * .param("prize.amount", "500.00").param("prize.currency", "EUR"))
	 * .andExpect(status().is3xxRedirection())
	 * .andExpect(view().name("redirect:/tournaments/all"));
	 * 
	 * }
	 * 
	 */
	
	
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessTournamentFormSuccess() throws Exception {
		mockMvc.perform(post("/tournaments/new").with(csrf()).param("name", "Betty").param("location", "Seville")
				.param("petType", "hamster").param("field", "Ice").param("applyDate", "2021/01/01")
				.param("category","Agility")
				.param("startDate", "2021/01/02").param("endDate", "2021/01/03")
				.param("prize.amount", "500.00").param("prize.currency", "EUR"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("welcome"));
	}
	

	@Test
	void testNotProcessTournamentFormSuccess() throws Exception {
		mockMvc.perform(
				post("/tournaments/new").with(csrf())
				.param("applyDate", "2020/04/01").param("startDate", "2020/05/01")
				.param("endDate","2020/07/01").param("name","Mordor")
				.param("location","Seville").param("prize.amount", "500.00")
				.param("category","Speed").param("field","Ice").param("judge","Peter")
				.param("prize.currency","EUR").param("petType","hamster"))
				.andExpect(status().is4xxClientError());
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

}
