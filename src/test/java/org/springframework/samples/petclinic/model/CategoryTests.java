package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.samples.petclinic.web.ApplicationValidator;
import org.springframework.samples.petclinic.web.CategoryValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class CategoryTests {
	
	//OJO Aquí habria que crear tournamentValidator
	/*
	 * private TournamentValidator createValidator() { LocalValidatorFactoryBean
	 * localValidatorFactoryBean = new LocalValidatorFactoryBean();
	 * localValidatorFactoryBean.afterPropertiesSet(); return (TournamentValidator)
	 * localValidatorFactoryBean.getValidator(); }
	 */
		

	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	//  Create new Category Positive Case: 
	@Test
	void shouldValidateCategory() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Category category = new Category();		
		category.setName("Horses");
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);		
		assertThat(constraintViolations.size()).isEqualTo(0);

		CategoryValidator categoryValidator = new CategoryValidator();
		Errors errors = new BeanPropertyBindingResult(category, "category");
		categoryValidator.validate(category, errors);
	
		assertThat(errors.hasErrors()).isEqualTo(false);	
		assertThat(errors.getErrorCount()).isEqualTo(0);			
		
	}
	
	//  Create new Category Negative Case: invalid name input 
	@Test
	void shouldNotValidateName() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Category category = new Category();		
		category.setName("");
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Category> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");

	}
	
	

	

}
