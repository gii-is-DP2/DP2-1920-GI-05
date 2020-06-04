package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.web.OwnerController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerControllerIntegrationTests {

	@Autowired
	private OwnerController ownerController;
	
	@Autowired
	private  OwnerService ownerService;


	@Test
	void testInitCreationForm() throws Exception {
		
		ModelMap model=new ModelMap();		
		
		String view= this.ownerController.initCreationForm(model);
		
		assertEquals(view,"owners/createOrUpdateOwnerForm");
		assertNotNull(model.get("owner"));			
	}

	@Test
	void testProcessCreationForm() throws Exception {
		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");	

		String view= this.ownerController.processCreationForm(
			ownerService.findOwnerById(1), bindingResult);
		assertEquals(view,"welcome");	
	}


	@Test
	void testInitFindForm() throws Exception {
		
		ModelMap model=new ModelMap();		
		
		String view= this.ownerController.initFindForm(model);
		
		assertEquals(view,"owners/findOwners");
		assertNotNull(model.get("owner"));				
	}
	
	@Test
	void testProcessFindForm() throws Exception {
		
		ModelMap model=new ModelMap();		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");

		String view= this.ownerController.processFindForm(
			ownerService.findOwnerById(1), bindingResult, model);
		
		assertEquals(view,"redirect:/owners/1");	
	}
	
}
