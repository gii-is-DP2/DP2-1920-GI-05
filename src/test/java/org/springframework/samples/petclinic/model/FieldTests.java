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
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class FieldTests {
	
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

	@Test
	void shouldNotValidateName() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
				
		Field field = new Field();
		field.setBreadth("100");
		field.setLenght("1000");
		field.setName("me");
		field.setPhotoURL("https://alliancecincinnati.com/wp-content/uploads/2019/08/Dog-Days-Field-Map-2019.jpg");
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Field>> constraintViolations = validator.validate(field);		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Field> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");

	}
	
	
	@Test
	void shouldNotValidateBreadth() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
				
		Field field = new Field();
		field.setBreadth("99999999");
		field.setLenght("1000");
		field.setName("Map 1");
		field.setPhotoURL("https://alliancecincinnati.com/wp-content/uploads/2019/08/Dog-Days-Field-Map-2019.jpg");
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Field>> constraintViolations = validator.validate(field);		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Field> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("breadth");
		assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<5 digits>.<2 digits> expected)");

	}
	

	@Test
	void shouldNotValidateLength() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
					
		Field field = new Field();
		field.setBreadth("500");
		field.setLenght("99999999");
		field.setName("Map 2");
		field.setPhotoURL("https://helgehimleagilitycourses.files.wordpress.com/2019/09/dm-jump-team.gif?w=676");
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Field>> constraintViolations = validator.validate(field);		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Field> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("lenght");
		assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<5 digits>.<2 digits> expected)");

	}
	
	@Test
	void shouldNotValidatePhoto() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
			
		Field field = new Field();
		field.setBreadth("500");
		field.setLenght("1000");
		field.setName("Map 3");
		field.setPhotoURL("photo");
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Field>> constraintViolations = validator.validate(field);		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Field> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("photoURL");
		assertThat(violation.getMessage()).isEqualTo("must be a valid URL");

	}
	

}
