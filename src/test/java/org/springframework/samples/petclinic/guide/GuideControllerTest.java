package org.springframework.samples.petclinic.guide;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Guide;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.GuideService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ReportService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.web.GuideController;
import org.springframework.samples.petclinic.web.PetFormatter;
import org.springframework.samples.petclinic.web.ReportFormatter;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = GuideController.class, includeFilters = @ComponentScan.Filter(value = { PetFormatter.class,
		ReportFormatter.class }, type = FilterType.ASSIGNABLE_TYPE), excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class GuideControllerTest {
	
	private static final int TEST_GUIDE_ID = 1;
  private static final int TEST_PET_ID = 1;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PetService petService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private GuideService guideService;
	
	@MockBean
	private ReportService reportService;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	@Autowired
	private GuideController guideController;
	
	
	@BeforeEach
	void setup() {
		
		Guide guide = new Guide();
		guide.setId(2);
		guide.setLastName("Cesar");
		
    	Pet pet = new Pet();
		pet.setId(TEST_PET_ID);
		pet.setName("Leo");

		given(this.petService.findPetByGuideId(TEST_PET_ID)).willReturn(Lists.newArrayList(pet));


		Pet pet1 = new Pet();
		pet1.setId(10);
		given(this.petService.findPetByGuideId(TEST_GUIDE_ID)).willReturn(Lists.newArrayList(pet1));

		
	}
	
	@WithMockUser(value = "spring")
	@Test	
	void testListAll() throws Exception {
		mockMvc.perform(get("/guides/{guideId}/pets", TEST_GUIDE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("pets")).andExpect(view().name("guides/pets"));
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testListGuideReports() throws Exception {
		mockMvc.perform(get("/guides/{guideId}/reports", TEST_GUIDE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("reports")).andExpect(view().name("guides/reports"));
	}


}
