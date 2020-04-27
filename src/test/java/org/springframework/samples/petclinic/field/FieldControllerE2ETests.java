package org.springframework.samples.petclinic.field;

import static org.mockito.BDDMockito.given;
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
import org.springframework.samples.petclinic.service.exceptions.DuplicateFieldNameException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class FieldControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;	
	
	// List Fields Postive Case
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testListAll() throws Exception {
		mockMvc.perform(get("/fields/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("fields")).andExpect(view().name("fields/list"));
	}
	
	// Get Create Fields Positive Case
	@WithMockUser(username="admin1",authorities= {"admin"})
	@Test
	void testGetNewFields() throws Exception {
		mockMvc.perform(get("/fields/new")).andExpect(status().isOk())
		.andExpect(model().attributeExists("field")).andExpect(view().name("fields/createOrUpdateFieldForm"));
	}
	
	// Create Fields Negative Case: Duplicated Name
	@WithMockUser(username="admin1",authorities= {"admin"})
    @Test
void testProcessCreationFormSuccess() throws Exception, DuplicateFieldNameException {
	mockMvc.perform(post("/fields/new")
						.with(csrf())
						.param("name", "Map 5")
						.param("breadth", "100.00")
						.param("lenght", "100.00")
						.param("photoURL", "https://helgehimleagilitycourses.files.wordpress.com/2019/09/dm-jump-team.gif?w=676"))				
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/fields/all"));
}	
	
	// Create Fields Negative Case: Duplicated Name
	@WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void testProcessCreationFormHasNameErrors() throws Exception, DuplicateFieldNameException {
		mockMvc.perform(post("/fields/new")
							.with(csrf())
							//.param("name", "Map 1")
							.param("breadth", "100.00")
							.param("lenght", "100.00")
							.param("photoURL", "https://helgehimleagilitycourses.files.wordpress.com/2019/09/dm-jump-team.gif?w=676"))				
				.andExpect(model().attributeHasErrors("field"))
				.andExpect(status().isOk())
				.andExpect(view().name("fields/createOrUpdateFieldForm"));
					
	}
	
	// Create Fields Negative breadth is too long
	@WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void testProcessCreationFormHasIntegerErrors1() throws Exception {
		mockMvc.perform(post("/fields/new")
							.with(csrf())
							.param("name", "Map 2")
							.param("breadth", "100000.00")
							.param("lenght", "100.00")
							.param("photoURL", "https://helgehimleagilitycourses.files.wordpress.com/2019/09/dm-jump-team.gif?w=676"))				
				.andExpect(model().attributeHasErrors("field"))
				.andExpect(status().isOk())
				.andExpect(view().name("fields/createOrUpdateFieldForm"));
					
	}
	
	// Create Fields Negative breadth is not a valid input, must be x.YY
	@WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void testProcessCreationFormHasFractionsErrors1() throws Exception {
		mockMvc.perform(post("/fields/new")
							.with(csrf())
							.param("name", "Map 2")
							.param("breadth", "100.009")
							.param("lenght", "100.00")
							.param("photoURL", "https://helgehimleagilitycourses.files.wordpress.com/2019/09/dm-jump-team.gif?w=676"))				
				.andExpect(model().attributeHasErrors("field"))
				.andExpect(status().isOk())
				.andExpect(view().name("fields/createOrUpdateFieldForm"));
		
	}
	
	// Create Fields Negative: length is too long
	@WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void testProcessCreationFormHasIntegerErrors2() throws Exception {
		mockMvc.perform(post("/fields/new")
							.with(csrf())
							.param("name", "Map 2")
							.param("breadth", "100.00")
							.param("lenght", "100000.00")
							.param("photoURL", "https://helgehimleagilitycourses.files.wordpress.com/2019/09/dm-jump-team.gif?w=676"))				
				.andExpect(model().attributeHasErrors("field"))
				.andExpect(status().isOk())
				.andExpect(view().name("fields/createOrUpdateFieldForm"));
					
	}
	
	// Create Fields Negative: length is not a valid input, must be x.YY
	@WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void testProcessCreationFormHasFractionsErrors2() throws Exception {
		mockMvc.perform(post("/fields/new")
							.with(csrf())
							.param("name", "Map 2")
							.param("breadth", "100.00")
							.param("lenght", "100.009")
							.param("photoURL", "https://helgehimleagilitycourses.files.wordpress.com/2019/09/dm-jump-team.gif?w=676"))				
				.andExpect(model().attributeHasErrors("field"))
				.andExpect(status().isOk())
				.andExpect(view().name("fields/createOrUpdateFieldForm"));
		
	}
	
	// Create Fields Negative: URL of photo is not a valid URL
	@WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void testProcessCreationFormHasURLErrors() throws Exception {
		mockMvc.perform(post("/fields/new")
							.with(csrf())
							.param("name", "Map 2")
							.param("breadth", "100.00")
							.param("lenght", "100.00")
							.param("photoURL", "Sample text"))				
				.andExpect(model().attributeHasErrors("field"))
				.andExpect(status().isOk())
				.andExpect(view().name("fields/createOrUpdateFieldForm"));
		
	}
	
	
}
