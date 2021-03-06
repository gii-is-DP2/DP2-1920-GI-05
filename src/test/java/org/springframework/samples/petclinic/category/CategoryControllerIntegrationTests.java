package org.springframework.samples.petclinic.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.util.Collections;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.web.CategoryController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerIntegrationTests {


	
	@Autowired
	private CategoryController categoryController;

	
    @Test
	void testListAllApplications() throws Exception {

		ModelMap model=new ModelMap();		
		String view = categoryController.CategoriesList(model);		
		
		assertEquals(view,"categories/list");
		assertNotNull(model.get("categories"));		
	}
    

    @Test
	void testInitCreationForm() throws Exception {

		ModelMap model=new ModelMap();		
		String view= categoryController.initCreationForm(model);
		
		assertEquals(view,"categories/createOrUpdateCategoryForm");
		assertNotNull(model.get("category"));		
	}
    

    @Test
    @Transactional
	void testProcessCreationFormSuccess()  throws Exception {
    	Category c= new Category();
    	ModelMap model=new ModelMap();		

    	c.setName("Vertical jump");

    	BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
    	String view=categoryController.processCreationForm(c, bindingResult, model);
 
		//assertThat(apps.size()).isEqualTo(7);
		assertEquals(view,"redirect:/categories/all");				
	}
    
    @Test    
	void testProcessCreationFormHasErrorsBlankName()  throws Exception {
    	Category c= new Category();
    	ModelMap model=new ModelMap();		

    	c.setName("");
    	
    	//HashMap <Object, Object> m = new HashMap <Object, Object> ();
    	
    	BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
    	bindingResult.reject("name", "Requied!");
    	String view=categoryController.processCreationForm(c, bindingResult, model);
 
		//assertThat(apps.size()).isEqualTo(7);
		assertEquals(view,"categories/createOrUpdateCategoryForm");				
	}
    
    @Test
	void testProcessCreationFormHasErrorsWrongName()  throws Exception {
    	Category c= new Category();
    	ModelMap model=new ModelMap();		

    	c.setName("Agility");
    	
    	//HashMap <Object, Object> m = new HashMap <Object, Object> ();
    	
    	BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
    	bindingResult.reject("name", "Requied!");
    	String view=categoryController.processCreationForm(c, bindingResult, model);
 
		//assertThat(apps.size()).isEqualTo(7);
		assertEquals(view,"categories/createOrUpdateCategoryForm");				
	}
    

    
	
}
