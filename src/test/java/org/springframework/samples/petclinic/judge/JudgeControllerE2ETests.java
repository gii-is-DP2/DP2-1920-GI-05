package org.springframework.samples.petclinic.judge;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class JudgeControllerE2ETests {
	
	private static final int TEST_JUDGE_ID = 1;
	
	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test	
	void testInitCreateJudge() throws Exception {
		mockMvc.perform(get("/judges/new")).andExpect(status().isOk())
				.andExpect(model().attributeExists("judge")).andExpect(view().name("judges/createOrUpdateJudgeForm"));
	}
	
	@WithMockUser(username="judge1",authorities= {"judge"})
	@Test	
	void testJudgeDetails() throws Exception {
		mockMvc.perform(get("/judges/details")).andExpect(status().isOk())
				.andExpect(view().name("judges/judgeDetails"));
	}
	
	@WithMockUser(username="judge1",authorities= {"judge"})
	@Test	
	void testInitEditJudge() throws Exception {
		mockMvc.perform(get("/judges/{judgeId}/edit", TEST_JUDGE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("judge")).andExpect(view().name("judges/createOrUpdateJudgeForm"));
	}
	
	@WithMockUser(username="judge1",authorities= {"judge"})
	@Test	
	void testListAll() throws Exception {
		mockMvc.perform(get("/judges/{judgeId}/tournaments", TEST_JUDGE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("tournaments")).andExpect(view().name("judges/tournaments"));
	}
	
	
	
	

}
