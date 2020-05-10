package org.springframework.samples.petclinic.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.web.FieldValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class FieldValidatorTests {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	// Create new Field Positive Case
	@Test
	void shouldValidateField() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
				
		Field field = new Field();
		field.setName("Map 150");
		field.setWidth(100.00);
		field.setLenght(600.00);
		field.setPhotoURL("https://alliancecincinnati.com/wp-content/uploads/2019/08/Dog-Days-Field-Map-2019.jpg");
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Field>> constraintViolations = validator.validate(field);		
		assertThat(constraintViolations.size()).isEqualTo(0);
		
		FieldValidator fieldValidator = new FieldValidator();

		Errors errors = new BeanPropertyBindingResult(field, "field");
		fieldValidator.validate(field, errors);
		assertThat(errors.getErrorCount()).isEqualTo(0);	
		assertThat(errors.hasErrors()).isEqualTo(false);	
		}
	
	// Create new Field Negative Case: Invalid name input
	@Test
	void shouldNotValidateName() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
				
		Field field = new Field();
		field.setWidth(100.00);
		field.setLenght(600.00);
		field.setName("me");
		field.setPhotoURL("https://alliancecincinnati.com/wp-content/uploads/2019/08/Dog-Days-Field-Map-2019.jpg");
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Field>> constraintViolations = validator.validate(field);		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Field> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");

	}
	
	// Create new Field Negative Case: Invalid breath input
	@Test
	void shouldNotValidateWidth() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
				
		Field field = new Field();
		field.setWidth(99999999.00);
		field.setLenght(1000.00);
		field.setName("Map 1");
		field.setPhotoURL("https://alliancecincinnati.com/wp-content/uploads/2019/08/Dog-Days-Field-Map-2019.jpg");
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Field>> constraintViolations = validator.validate(field);		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Field> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("width");
		assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<5 digits>.<2 digits> expected)");

	}
	
	// Create new Field Negative Case: Invalid length input
	@Test
	void shouldNotValidateLength() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
					
		Field field = new Field();
		field.setWidth(500.00);
		field.setLenght(99999999.00);
		field.setName("Map 2");
		field.setPhotoURL("https://helgehimleagilitycourses.files.wordpress.com/2019/09/dm-jump-team.gif?w=676");
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Field>> constraintViolations = validator.validate(field);		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Field> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("lenght");
		assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<5 digits>.<2 digits> expected)");

	}
	
	// Create new Field Negative Case: Invalid photo input
	@Test
	void shouldNotValidatePhoto() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
			
		Field field = new Field();
		field.setWidth(500.00);
		field.setLenght(1000.00);
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
