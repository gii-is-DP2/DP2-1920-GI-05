package org.springframework.samples.petclinic.guide;

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
public class GuideControllerE2ETests {
	
	private static final int TEST_GUIDE_ID = 1;
	
	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test	
	void testInitCreateJudge() throws Exception {
		mockMvc.perform(get("/guide/new")).andExpect(status().isOk())
				.andExpect(model().attributeExists("guide")).andExpect(view().name("guides/createOrUpdateGuideForm"));
	}
	
	@WithMockUser(username="guide1",authorities= {"guide"})
	@Test	
	void testJudgeDetails() throws Exception {
		mockMvc.perform(get("/guides/details")).andExpect(status().isOk())
				.andExpect(view().name("guides/guideDetails"));
	}
	
	@WithMockUser(username="guide1",authorities= {"guide"})
	@Test	
	void testInitEditGuide() throws Exception {
		mockMvc.perform(get("/guides/{guideId}/edit", TEST_GUIDE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("guide")).andExpect(view().name("guides/createOrUpdateGuideForm"));
	}
	
	@WithMockUser(username="guide1",authorities= {"guide"})
	@Test	
	void testListAll() throws Exception {
		mockMvc.perform(get("/guides/{guideId}/pets", TEST_GUIDE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("pets")).andExpect(view().name("guides/pets"));
	}
		
	@WithMockUser(username = "guide1", authorities = {"guide"})
	@Test
	void testListGuideReports() throws Exception {
		mockMvc.perform(get("/guides/{guideId}/reports", TEST_GUIDE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("reports")).andExpect(view().name("guides/reports"));
	}


}
