package org.springframework.samples.petclinic.category;

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
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.web.ApplicationController;
import org.springframework.samples.petclinic.web.CategoryController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryIntegrationTests {


	
	@Autowired
	private CategoryController categoryController;
	
	@Autowired
	private CategoryService categoryService;


	


	
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
	void testProcessCreationFormSuccess()  throws Exception {
    	Category c= new Category();
    	ModelMap model=new ModelMap();		

    	c.setName("Vertical jump");

    	BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
    	String view=categoryController.processCreationForm(c, bindingResult, model);
 
		Collection<Category> all = this.categoryService.findAllCategories();
		//assertThat(apps.size()).isEqualTo(7);
		assertEquals(view,"redirect:/categories/all");				
	}
    
    @Test
	void testProcessCreationFormHasErrorsBlankName()  throws Exception {
    	Category c= new Category();
    	ModelMap model=new ModelMap();		

    	//c.setName("");

    	BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
    	bindingResult.reject("name", "Requied!");
    	String view=categoryController.processCreationForm(c, bindingResult, model);
 
		Collection<Category> all = this.categoryService.findAllCategories();
		//assertThat(apps.size()).isEqualTo(7);
		assertEquals(view,"categories/createOrUpdateCategoryForm");				
	}
    
    @Test
	void testProcessCreationFormHasErrorsWrongName()  throws Exception {
    	Category c= new Category();
    	ModelMap model=new ModelMap();		

    	c.setName("Agility");

    	BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
    	bindingResult.reject("name", "Requied!");
    	String view=categoryController.processCreationForm(c, bindingResult, model);
 
		Collection<Category> all = this.categoryService.findAllCategories();
		//assertThat(apps.size()).isEqualTo(7);
		assertEquals(view,"categories/createOrUpdateCategoryForm");				
	}
    

    
	
}
