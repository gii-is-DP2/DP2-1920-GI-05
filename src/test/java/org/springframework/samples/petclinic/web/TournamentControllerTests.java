package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
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

@WebMvcTest(controllers = TournamentController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class TournamentControllerTests {

	private static final int TEST_TOURNAMENT_ID = 1;

	@Autowired
	private TournamentController tournamentController;
	
	@MockBean
	private TournamentService clinicService;
	
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

	@Autowired
	private MockMvc mockMvc;

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
