package org.springframework.samples.petclinic.judge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.web.JudgeController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = JudgeController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
classes = WebSecurityConfigurer.class), 
excludeAutoConfiguration = SecurityConfiguration.class)
public class JudgeControllerTest {
	
	private static final int TEST_JUDGE_ID = 1;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TournamentService tournamentService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private JudgeService judgeService;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	@Autowired
	private JudgeController judgeController;
	
	
	@BeforeEach
	void setup() {
				
		
	}
	
	@WithMockUser(value = "spring")
	@Test	
	void testListAll() throws Exception {
		mockMvc.perform(get("/judges/{judgeId}/tournaments", TEST_JUDGE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("tournaments")).andExpect(view().name("judges/tournaments"));
	}
	
	
	
	

}
