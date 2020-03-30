package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collection;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicateFieldNameException;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@WebMvcTest(controllers = FieldController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class FieldControllerTests {


	@Autowired
	private FieldController FieldController;
	
	@MockBean
	private FieldService fieldService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		Field field1 = new Field();
		field1.setName("Map 1");
		field1.setBreadth("100.00");
		field1.setLenght("100.00");
		field1.setPhotoURL("https://helgehimleagilitycourses.files.wordpress.com/2019/09/dm-jump-team.gif?w=676");
		Collection<Field> fields = this.fieldService.findAllFields();
		given(this.fieldService.findAllFields()).willReturn(fields);	

	}

	// List Fields Postive Case
	@WithMockUser(value = "spring")
	@Test
	void testListAll() throws Exception {
		mockMvc.perform(get("/fields/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("fields")).andExpect(view().name("fields/list"));
	}
	
	// Create Fields Positive Case: all valid inputs
	@WithMockUser(value = "spring")
	@Test
	void testGetNewFields() throws Exception {
		mockMvc.perform(get("/fields/new")).andExpect(status().isOk())
		.andExpect(model().attributeExists("field")).andExpect(view().name("fields/createOrUpdateFieldForm"));
	}
	
	// Create Fields Negative Case: Duplicated Name
	@WithMockUser(value = "spring")
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
	@WithMockUser(value = "spring")
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
	@WithMockUser(value = "spring")
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
	@WithMockUser(value = "spring")
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
	@WithMockUser(value = "spring")
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
	@WithMockUser(value = "spring")
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
	@WithMockUser(value = "spring")
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
