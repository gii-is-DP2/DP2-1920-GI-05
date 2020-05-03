package org.springframework.samples.petclinic.owner;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class OwnerControllerE2ETest {

	private static final int TEST_OWNER_ID = 1;

	@Autowired
	private MockMvc mockMvc;
	
	// Test

	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testListOwner() throws Exception {
	
		mockMvc.perform(get("/owners"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/ownersList"));
	}

	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testFindOwner() throws Exception {
		mockMvc.perform(get("/owners/find"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("owner"))
			.andExpect(view().name("owners/findOwners"));
	}

	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testFindOwnerError() throws Exception {
		mockMvc.perform(get("/owners").param("lastName", "Unknown Surname"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasFieldErrors("owner", "lastName"))
			.andExpect(model().attributeHasFieldErrorCode("owner", "lastName", "notFound"))
			.andExpect(view().name("owners/findOwners"));
	}

	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testFindOwnerByLastName() throws Exception {
	
		mockMvc.perform(get("/owners").param("lastName", "Franklin"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/" + TEST_OWNER_ID));
	}

	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testShowOwner() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/show", TEST_OWNER_ID))
			.andExpect(status().isOk())
			.andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))))
			.andExpect(model().attribute("owner", hasProperty("firstName", is("George"))))
			.andExpect(model().attribute("owner", hasProperty("address", is("110 W. Liberty St."))))
			.andExpect(model().attribute("owner", hasProperty("city", is("Madison"))))
			.andExpect(model().attribute("owner", hasProperty("telephone", is("6085551023"))))
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testCreateOwnerGet() throws Exception {
		mockMvc.perform(get("/users/new")).andExpect(status().isOk())
			.andExpect(model().attributeExists("owner"))
			.andExpect(view().name("users/createOwnerForm"));
	}

	@Test
	void testCreateOwnerSuccess() throws Exception {
		mockMvc.perform(post("/owners/new").param("firstName", "Joe").param("lastName", "Bloggs")
			.with(csrf())
			.param("address", "123 Caramel Street")
			.param("city", "London")
			.param("telephone", "01316761638"))
			.andExpect(status().is3xxRedirection());
	}

	@Test
	void testCreateOwnerError() throws Exception {
		mockMvc.perform(post("/users/new")
			.with(csrf()).param("firstName", "Joe").param("lastName", "Bloggs")
				.param("city", "London"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("owner"))
				.andExpect(model().attributeHasFieldErrors("owner", "address"))
				.andExpect(model().attributeHasFieldErrors("owner", "telephone"))
				.andExpect(view().name("users/createOwnerForm"));
	}

	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testUpdateOwnerGet() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/edit", TEST_OWNER_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("owner"))
			.andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))))
			.andExpect(model().attribute("owner", hasProperty("firstName", is("George"))))
			.andExpect(model().attribute("owner", hasProperty("address", is("110 W. Liberty St."))))
			.andExpect(model().attribute("owner", hasProperty("city", is("Madison"))))
			.andExpect(model().attribute("owner", hasProperty("telephone", is("6085551023"))))
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testUpdateOwnerSuccess() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID)
			.with(csrf())
			.param("firstName", "Joe")
			.param("lastName", "Bloggs")
			.param("address", "123 Caramel Street")
			.param("city", "London")
			.param("telephone", "01616291589")
			.param("username", "owner756")
			.param("password", "own3rPass"))
			.andExpect(status().is2xxSuccessful());
			/* .andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/details")); */
	}


	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testUpdateOwnerError() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID)
			.with(csrf())
			.param("firstName", "Joe")
			.param("lastName", "Bloggs")
			.param("city", "London"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("owner"))
			.andExpect(model().attributeHasFieldErrors("owner", "address"))
			.andExpect(model().attributeHasFieldErrors("owner", "telephone"))
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}


}
