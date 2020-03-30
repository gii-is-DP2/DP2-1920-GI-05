package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

@WebMvcTest(controllers = CategoryController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
classes = WebSecurityConfigurer.class), 
excludeAutoConfiguration = SecurityConfiguration.class)
class CategoryControllerTests {

	@Autowired
	private CategoryController categoryController;

	@MockBean
	private CategoryService categoryService;

	@Autowired
	private MockMvc mockMvc;

	// List Categories: Positive case
	@WithMockUser(username = "spring")
	@Test
	void testListAll() throws Exception {
		mockMvc.perform(get("/categories/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("categories")).andExpect(view().name("categories/list"));
	}

	// Create category Positive Case: all valid inputs
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/categories/new").with(csrf()).param("name", "Betty"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/categories/all"));

	}
	
	// Create category Negative Case: invalid input 
	@WithMockUser(value = "spring")
	@Test
	void testNotProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/categories/new").with(csrf())).andExpect(model().attributeHasErrors("category"))
				.andExpect(status().isOk());
	}

}
