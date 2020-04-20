package org.springframework.samples.petclinic.field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.samples.petclinic.web.FieldController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FieldIntegrationTests {

	@Autowired
	private FieldController fieldController;
	
	@Autowired
	private FieldService fieldService;
	
    @Test
	void testListAllField() throws Exception {

		ModelMap model=new ModelMap();		
		String view = this.fieldController.showFields(model);
		
		assertEquals(view,"fields/list");
		assertNotNull(model.get("fields"));		
	}
    
    @Test
	void testInitCreationForm() throws Exception {

		ModelMap model=new ModelMap();		
		String view= this.fieldController.initCreationForm(model);
		
		assertEquals(view,"fields/createOrUpdateFieldForm");
		assertNotNull(model.get("field"));		
	}
    
    @Test
	void testProcessCreationForm() throws Exception {

		ModelMap model=new ModelMap();		
		Field field = new Field();
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		field.setBreadth(100.00);
		field.setLenght(200.00);
		field.setName("Test field");
		field.setPhotoURL("https://www.youtube.com/watch?v=Q6aRJgQ9s6Q");
		
		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
		assertEquals(view,"redirect:/fields/all");
	}
    
    @Test
  	void testShouldNotProcessCreateName() throws Exception {

  		ModelMap model=new ModelMap();		
  		Field field = new Field();
  		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
  		field.setBreadth(100.00);
  		field.setLenght(200.00);
  		field.setName("no");
  		field.setPhotoURL("https://www.youtube.com/watch?v=Q6aRJgQ9s6Q");
  		
  		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
  		assertEquals(view,"fields/createOrUpdateFieldForm");
  	}
    
    @Test
 	void testShouldNotProcessCreationBreadth1() throws Exception {

 		ModelMap model=new ModelMap();		
 		Field field = new Field();
 		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
 		field.setBreadth(-100.00);
 		field.setLenght(200.00);
 		field.setName("Test field");
 		field.setPhotoURL("https://www.youtube.com/watch?v=Q6aRJgQ9s6Q");
 		
 		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
 		assertEquals(view,"fields/createOrUpdateFieldForm");
 	}
    
    @Test
 	void testShouldNotProcessCreationBreadth2() throws Exception {

 		ModelMap model=new ModelMap();		
 		Field field = new Field();
 		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
 		field.setBreadth(100.009);
 		field.setLenght(200.00);
 		field.setName("Test field");
 		field.setPhotoURL("https://www.youtube.com/watch?v=Q6aRJgQ9s6Q");
 		
 		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
 		assertEquals(view,"fields/createOrUpdateFieldForm");
 	}
    
    @Test
 	void testShouldNotProcessCreationLenght1() throws Exception {

 		ModelMap model=new ModelMap();		
 		Field field = new Field();
 		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
 		field.setBreadth(100.00);
 		field.setLenght(-200.00);
 		field.setName("Test field");
 		field.setPhotoURL("https://www.youtube.com/watch?v=Q6aRJgQ9s6Q");
 		
 		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
 		assertEquals(view,"fields/createOrUpdateFieldForm");
 	}
    
    @Test
 	void testShouldNotProcessCreationLenght2() throws Exception {

 		ModelMap model=new ModelMap();		
 		Field field = new Field();
 		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
 		field.setBreadth(100.00);
 		field.setLenght(200.008);
 		field.setName("Test field");
 		field.setPhotoURL("https://www.youtube.com/watch?v=Q6aRJgQ9s6Q");
 		
 		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
 		assertEquals(view,"fields/createOrUpdateFieldForm");
 	}
    
    @Test
	void testShouldNotProcessCreationPhoto() throws Exception {

		ModelMap model=new ModelMap();		
		Field field = new Field();
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		field.setBreadth(100.00);
		field.setLenght(200.00);
		field.setName("Test field");
		field.setPhotoURL("NotURL");
		
		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
		assertEquals(view,"fields/createOrUpdateFieldForm");
	}
    
	
}
