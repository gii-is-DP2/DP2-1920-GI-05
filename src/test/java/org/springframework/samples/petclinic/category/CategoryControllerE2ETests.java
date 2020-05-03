package org.springframework.samples.petclinic.category;


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
/*@TestPropertySource(
  locations = "classpath:application-mysql.properties")*/
public class CategoryControllerE2ETests {

	
	@Autowired
	private MockMvc mockMvc;	
	
	// List Fields Postive Case
	@WithMockUser(username="admin1", authorities= {"admin"})
	@Test
	void testListAll() throws Exception {
		mockMvc.perform(get("/categories/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("categories")).andExpect(view().name("categories/list"));
	}
	
	// Get Create Fields Positive Case
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/categories/new").with(csrf()).param("name", "Betty"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/categories/all"));
	}
	
	// Create Fields Negative Case: Duplicated Name
	@WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void testNotProcessCreationFormDuplicatedName() throws Exception {
		mockMvc.perform(post("/categories/new").with(csrf()).param("name", "Speed")).andExpect(model().attributeHasErrors("category"))
				.andExpect(status().isOk());
	}	
	
	// Create Fields Negative Case: Blank Name
	@WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void testNotProcessCreationFormBlankName() throws Exception {
		mockMvc.perform(post("/categories/new").with(csrf()).param("name", "")).andExpect(model().attributeHasErrors("category"))
				.andExpect(status().isOk());
	}	
	

	

	
	
}
