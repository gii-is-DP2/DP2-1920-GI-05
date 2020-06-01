package org.springframework.samples.petclinic.field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.web.FieldController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FieldControllerIntegrationTests {

	@Autowired
	private FieldController fieldController;
	
	
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
    @Transactional
	void testProcessCreationForm() throws Exception {

		ModelMap model=new ModelMap();		
		Field field = new Field();
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		field.setWidth(100.00);
		field.setLenght(200.00);
		field.setName("Test field");
		field.setPhotoURL("https://www.youtube.com/watch?v=Q6aRJgQ9s6Q");
		
		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
		assertEquals(view,"redirect:/fields/all");
	}
    
    @Test
  	void testShouldNotProcessCreateName() throws Exception {

  		Field field = new Field();
  		ModelMap model=new ModelMap();		

  		field.setWidth(100.00);
  		field.setLenght(200.00);
  		field.setName("");
  		field.setPhotoURL("https://www.youtube.com/watch?v=Q6aRJgQ9s6Q");
  		
  		BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
  		bindingResult.reject("name", "Requied!");
  		
  		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
  		
  		assertEquals(view,"fields/createOrUpdateFieldForm");
  	}
    
    @Test
 	void testShouldNotProcessCreationWidth1() throws Exception {

 		ModelMap model=new ModelMap();		
 		Field field = new Field();
 		BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
 		field.setWidth(-100.00);
 		field.setLenght(200.00);
 		field.setName("Test field");
 		field.setPhotoURL("https://www.youtube.com/watch?v=Q6aRJgQ9s6Q");
 		
		bindingResult.reject("width", "Required!");
 		
 		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
 		assertEquals(view,"fields/createOrUpdateFieldForm");
 	}
    
    @Test
 	void testShouldNotProcessCreationWidth2() throws Exception {

 		ModelMap model=new ModelMap();		
 		Field field = new Field();
 		BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
 		field.setWidth(100.009);
 		field.setLenght(200.00);
 		field.setName("Test field");
 		field.setPhotoURL("https://www.youtube.com/watch?v=Q6aRJgQ9s6Q");
 		
 		bindingResult.reject("width", "Required!");
 		
 		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
 		assertEquals(view,"fields/createOrUpdateFieldForm");
 	}
    
    @Test
 	void testShouldNotProcessCreationLenght1() throws Exception {

 		ModelMap model=new ModelMap();		
 		Field field = new Field();
 		BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
 		field.setWidth(100.00);
 		field.setLenght(-200.00);
 		field.setName("Test field");
 		field.setPhotoURL("https://www.youtube.com/watch?v=Q6aRJgQ9s6Q");
 		
 		bindingResult.reject("length", "Required!");
 		
 		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
 		assertEquals(view,"fields/createOrUpdateFieldForm");
 	}
    
    @Test
 	void testShouldNotProcessCreationLenght2() throws Exception {

 		ModelMap model=new ModelMap();		
 		Field field = new Field();
 		BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
 		field.setWidth(100.00);
 		field.setLenght(200.008);
 		field.setName("Test field");
 		field.setPhotoURL("https://www.youtube.com/watch?v=Q6aRJgQ9s6Q");
 		
 		bindingResult.reject("length", "Required!");
 		
 		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
 		assertEquals(view,"fields/createOrUpdateFieldForm");
 	}
    
    @Test
	void testShouldNotProcessCreationPhoto() throws Exception {

		ModelMap model=new ModelMap();		
		Field field = new Field();
		BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
		field.setWidth(100.00);
		field.setLenght(200.00);
		field.setName("Test field");
		field.setPhotoURL("NotURL");
		
		bindingResult.reject("photoUrl", "Required!");
		
		String view= this.fieldController.processCreationForm(field, bindingResult, model);		
		assertEquals(view,"fields/createOrUpdateFieldForm");
	}
    
	
}
