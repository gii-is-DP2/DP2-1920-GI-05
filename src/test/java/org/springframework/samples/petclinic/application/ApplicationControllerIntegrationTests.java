package org.springframework.samples.petclinic.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.ApplicationPOJO;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.web.ApplicationController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.mysql.cj.xdevapi.Result;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationControllerIntegrationTests {

	private static final int TEST_OWNER_ID = 1;
	private static final int TEST_TOURNAMENT_ID = 6;
	private static final int TEST_APPLICATION_ID = 3;
	private static final int TEST_PET_ID_1 = 1;
	
	@Autowired
	private ApplicationController applicationController;
	
	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private PetService petService;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private TournamentService tournamentService;
	
	@WithMockUser(username="owner1",authorities= {"owner"})
    @Test
	void testListOwnerApplications() throws Exception {

		ModelMap model=new ModelMap();		
		String view = applicationController.MyApplicationsList(model);		
		
		assertEquals(view,"applications/list");
		assertNotNull(model.get("applications"));		
	}
	
	
    @Test
	void testListAllApplications() throws Exception {

		ModelMap model=new ModelMap();		
		String view = applicationController.ApplicationList(model);		
		
		assertEquals(view,"applications/list");
		assertNotNull(model.get("applications"));		
	}
    
    //@WithMockUser(username="owner1",authorities= {"owner"})
    @Test
	void testInitCreationForm() throws Exception {

		ModelMap model=new ModelMap();		
		String view= this.applicationController.initCreateApplicationForm(TEST_TOURNAMENT_ID,  model);
		
		assertEquals(view,"applications/createApplicationForm");
		assertNotNull(model.get("applicationPOJO"));		
	}
    
    @WithMockUser(username="owner1",authorities= {"owner"})
    @Test
    @Transactional
	void testProcessCreationFormSuccess()  throws Exception {
    	ApplicationPOJO ap= new ApplicationPOJO();
    	ModelMap model=new ModelMap();		
    	Pet p = this.petService.findPetById(TEST_PET_ID_1);
    	
    	ap.setCreditCard("352571631239294");
    	ap.setPet(p);
    	
    	BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
    	String view= this.applicationController.processCreateForm(ap, bindingResult, TEST_TOURNAMENT_ID, model);
     	
    	if (bindingResult.hasErrors()) {			
		Collection<Application> apps = this.applicationService.findAllApplications();
		assertThat(apps.size()).isEqualTo(7);
    	}
		assertEquals(view,"redirect:/applications/list_mine");				
	}
    
    @WithMockUser(username="owner1",authorities= {"owner"})
    @Test
	void testProcessCreationFormHasErrors()  throws Exception {
    	ApplicationPOJO ap= new ApplicationPOJO();
    	ModelMap model=new ModelMap();		
    	Pet p = this.petService.findPetById(TEST_PET_ID_1);
    	
    	ap.setCreditCard("1234");
    	ap.setPet(p);

    	BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
    	bindingResult.reject("creditCard", "Requied!");
    	String view= this.applicationController.processCreateForm(ap, bindingResult, TEST_TOURNAMENT_ID, model);
 
		Collection<Application> apps = this.applicationService.findAllApplications();
		assertThat(apps.size()).isEqualTo(9);
		assertEquals(view,"applications/createApplicationForm");				
	}
    
    @Test
	void testInitUpdateForm() throws Exception {

		ModelMap model=new ModelMap();		
		String view= this.applicationController.initUpdateApplicationForm(TEST_APPLICATION_ID, model);
		
		assertEquals(view,"applications/updateApplicationForm");
		assertNotNull(model.get("application"));		
	}
    
    @Test
	void testProcessUpdateFormSuccess()  throws Exception {
    	Application ap= this.applicationService.findApplicationById(TEST_APPLICATION_ID);
    	ModelMap model=new ModelMap();		

    	ap.setStatus("ACCEPTED");

    	BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
    	String view=applicationController.processUpdateForm(ap, bindingResult, TEST_APPLICATION_ID, model);
 
		assertEquals(view,"redirect:/applications/all");				
	}
    
   @Test
	void testProcessUpdateFormHasErrors()  throws Exception {
    	Application ap= this.applicationService.findApplicationById(TEST_APPLICATION_ID);
    	ModelMap model=new ModelMap();		

    	ap.setStatus("negative");

    	BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
    	bindingResult.reject("status", "Requied!");
    	String view=applicationController.processUpdateForm(ap, bindingResult, TEST_APPLICATION_ID, model);
 
		assertEquals(view,"applications/updateApplicationForm");				
	}
    
	
}
