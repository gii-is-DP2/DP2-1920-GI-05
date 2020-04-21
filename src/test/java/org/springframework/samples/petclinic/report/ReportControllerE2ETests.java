package org.springframework.samples.petclinic.report;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.PetclinicApplication;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.web.PetController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
/*@TestPropertySource(
  locations = "classpath:application-mysql.properties")*/
public class ReportControllerE2ETests {
	
	private static final int TEST_JUDGE_ID = 1;
	
	private static final int TEST_TOURNAMENT_ID = 2;

	@Autowired
	private MockMvc mockMvc;	

	@WithMockUser(username="judge1",authorities= {"judge"})
	@Test
	void testListJudgeReports() throws Exception {
		mockMvc.perform(get("/judges/{judgeId}/reports", TEST_JUDGE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("reports")).andExpect(view().name("reports/listJudge"));
	}
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testListOwnerReports() throws Exception {
		mockMvc.perform(get("/myReports/")).andExpect(status().isOk())
				.andExpect(model().attributeExists("reports")).andExpect(view().name("reports/listOwner"));
	}
	
	@WithMockUser(username="judge1",authorities= {"judge"})
	@Test
	void testGetCreateReport() throws Exception {
		mockMvc.perform(get("/judges/{judgeId}/reports/{tournamentsId}/new", TEST_JUDGE_ID, TEST_TOURNAMENT_ID)).andExpect(status().isOk())
		.andExpect(view().name("reports/create")).andExpect(model().attributeExists("report"));
	}
	
	@WithMockUser(username="judge1",authorities= {"judge"})
	@Test
	void testPostCreateReport() throws Exception {
		mockMvc.perform(post("/judges/{judgeId}/reports/{tournamentsId}/new",  TEST_JUDGE_ID, TEST_TOURNAMENT_ID)
		.with(csrf())
		.param("pet", "Rosy")
		.param("points", "100")
		.param("comments", "Perfect performances"))
		//.andExpect(model().attributeHasNoErrors("report"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/judges/{judgeId}/reports"));
	}
	
	@WithMockUser(username="judge1",authorities= {"judge"})
	@Test
	void testPostCreateReportHigherPoints() throws Exception {
		mockMvc.perform(post("/judges/{judgeId}/reports/{tournamentsId}/new",  TEST_JUDGE_ID, TEST_TOURNAMENT_ID)
		.with(csrf())
		.param("pet", "Rosy")
		.param("points", "101")
		.param("comments", "Perfect performances"))
		.andExpect(model().attributeHasErrors("report"))
		.andExpect(status().isOk())
		.andExpect(view().name("reports/create"));
	}
	
	@WithMockUser(username="judge1",authorities= {"judge"})
	@Test
	void testPostCreateReportNegativePoints() throws Exception {
		mockMvc.perform(post("/judges/{judgeId}/reports/{tournamentsId}/new",  TEST_JUDGE_ID, TEST_TOURNAMENT_ID)
		.with(csrf())
		.param("pet", "Rosy")
		.param("points", "-89")
		.param("comments", "Nothing"))
		.andExpect(model().attributeHasErrors("report"))
		.andExpect(status().isOk())
		.andExpect(view().name("reports/create"));
	}
	
	@WithMockUser(username="judge1",authorities= {"judge"})
	@Test
	void testPostCreateReportBlankComment() throws Exception {
		mockMvc.perform(post("/judges/{judgeId}/reports/{tournamentsId}/new",  TEST_JUDGE_ID, TEST_TOURNAMENT_ID)
		.with(csrf())
		.param("pet", "Rosy")
		.param("points", "89")
		.param("comments", ""))
		.andExpect(model().attributeHasErrors("report"))
		.andExpect(status().isOk())
		.andExpect(view().name("reports/create"));
	}

}
