package org.springframework.samples.petclinic.guide;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Guide;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.service.GuideService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.web.GuideController;
import org.springframework.samples.petclinic.web.JudgeController;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GuideControllerIntegrationTests {

	private static final int TEST_GUIDE_ID = 1;
	
	@Autowired
	private GuideController guideController;
	
	@Autowired
	private  GuideService guideService;
	
	@Autowired
	private  PetService petService;


	
	@Test	
	void testPetsGuideList() throws Exception {
		
		ModelMap model=new ModelMap();		
		String view= guideController.initPets4Guide(TEST_GUIDE_ID, model);
		
		
		assertEquals(view,"guides/pets");
		assertNotNull(model.get("pets"));		
	}
	
   

    @Test
	void testInitCreationForm() throws Exception {

		ModelMap model=new ModelMap();		
		String view= guideController.initCreationForm(model);
		
		assertEquals(view,"guides/createOrUpdateGuideForm");
		assertNotNull(model.get("guide"));		
	}
    

    @Test    
    @Transactional
	void testProcessCreationFormSuccess()  throws Exception {
    	Guide g= new Guide();
    	ModelMap model=new ModelMap();		
    	
    	org.springframework.samples.petclinic.model.User u = new 
    			org.springframework.samples.petclinic.model.User();
    	u.setUsername("Angelo");
    	u.setPassword("AngieRoller");
    	
    	g.setAddress("address");
    	g.setCity("Boston");
    	g.setFirstName("Michael");
    	g.setLastName("Angelo");
    	g.setTelephone("941345123");
    	g.setUser(u);

    	BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
    	String view= this.guideController.processCreationForm(g, bindingResult);
 
		Collection<Guide> all = this.guideService.findAllGuides();
		assertThat(all.size()).isEqualTo(3);
		assertEquals(view,"welcome");				
	}
    
 


    
	
}
