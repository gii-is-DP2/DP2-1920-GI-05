package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.web.ApplicationValidator;
import org.springframework.samples.petclinic.web.TournamentValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ApplicationTests {
	
	private Owner owner;
	private Tournament tournament;
	private Pet pet;
	
	
	@BeforeEach
	void setup() {
		
		owner = new Owner();
		owner.setFirstName("John Doe");
		
		tournament = new Tournament();
		tournament.setName("Quarentine's Tournament");
		
		pet = new Pet();
		pet.setName("Goomba");;		
		
	}
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	

	// Create new Application Negative Case: Invalid status input not blank
	@Test
	void shouldNotValidateBlankStatus() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Application application = new Application();
		application.setCreditCard("4065972557141631");
		application.setMoment(LocalDate.of(2000, 10, 12));
		application.setOwner(owner);
		application.setStatus("");
		application.setTournament(tournament);
		application.setPet(pet);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Application>> constraintViolations = validator.validate(application);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Application> violation = constraintViolations.iterator().next();		
		assertThat(violation.getPropertyPath().toString()).isEqualTo("status");
		//assertThat(violation.getMessage()).isEqualTo("must match + "^PENDING|APPROVED|REJECTED"");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");			
	}
	
	// Create new Application Negative Case: Invalid status 
	@Test
	void shouldNotValidateWrongStatus() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Application application = new Application();
		application.setCreditCard("4065972557141631");
		application.setMoment(LocalDate.of(2000, 10, 12));
		application.setOwner(owner);
		application.setStatus("Admitido");
		application.setTournament(tournament);
		application.setPet(pet);
		
		ApplicationValidator validator = new ApplicationValidator();

		Errors errors = new BeanPropertyBindingResult(application, "application");
		validator.validate(application, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.hasFieldErrors("status")).isEqualTo(true);	

	}
	
	// Create new Application Positive Case: 
	@Test
	void shouldValidateApplication() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Application application = new Application();
		application.setCreditCard("4065972557141631");
		application.setMoment(LocalDate.of(2000, 10, 12));
		application.setOwner(owner);
		application.setStatus("REJECTED");
		application.setTournament(tournament);
		application.setPet(pet);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Application>> constraintViolations = validator.validate(application);		
		assertThat(constraintViolations.size()).isEqualTo(0);
		
		ApplicationValidator applicationValidator = new ApplicationValidator();
		Errors errors = new BeanPropertyBindingResult(application, "application");
		applicationValidator.validate(application, errors);
	
		assertThat(errors.hasErrors()).isEqualTo(false);	
		assertThat(errors.getErrorCount()).isEqualTo(0);				
	}
	
	// Create new Application Negative Case: Invalid credit card number
	@Test
	void shouldNotValidateBlankCreditCard() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Application application = new Application();
		application.setCreditCard("49721");
		application.setMoment(LocalDate.of(2000, 10, 12));
		application.setOwner(owner);
		application.setStatus("REJECTED");
		application.setTournament(tournament);
		application.setPet(pet);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Application>> constraintViolations = validator.validate(application);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Application> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("creditCard");
		assertThat(violation.getMessage()).isEqualTo("invalid credit card number");
		
	}
	
	// Create new Application Negative Case: Invalid tournament input
	@Test
	void shouldNotValidateBlankTournament() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Application application = new Application();
		application.setCreditCard("4065972557141631");
		application.setMoment(LocalDate.of(2000, 10, 12));
		application.setOwner(owner);
		application.setStatus("REJECTED");
		application.setTournament(null);
		application.setPet(pet);
		
		
		ApplicationValidator validator = new ApplicationValidator();
		Errors errors = new BeanPropertyBindingResult(application, "application");
		validator.validate(application, errors);
	
		assertThat(errors.hasFieldErrors("tournament")).isEqualTo(true);	
		assertThat(errors.getErrorCount()).isEqualTo(1);				
	}
	
	// Create new Application Negative Case: Invalid application input
	@Test
	void shouldNotValidateBlankOwner() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Application application = new Application();
		application.setCreditCard("4065972557141631");
		application.setMoment(LocalDate.of(2000, 10, 12));
		application.setOwner(null);
		application.setStatus("REJECTED");
		application.setTournament(tournament);
		application.setPet(pet);
		
		
		ApplicationValidator validator = new ApplicationValidator();
		Errors errors = new BeanPropertyBindingResult(application, "application");
		validator.validate(application, errors);
	
		
		assertThat(errors.getErrorCount()).isEqualTo(1);			
		assertThat(errors.hasFieldErrors("owner")).isEqualTo(true);	
	}
	
	// Create new Application Negative Case: Invalid pet input
	@Test
	void shouldNotValidateBlankPet() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Application application = new Application();
		application.setCreditCard("4065972557141631");
		application.setMoment(LocalDate.of(2000, 10, 12));
		application.setOwner(owner);
		application.setStatus("REJECTED");
		application.setTournament(tournament);
		application.setPet(null);
		
		
		ApplicationValidator validator = new ApplicationValidator();
		Errors errors = new BeanPropertyBindingResult(application, "application");
		validator.validate(application, errors);
	
		
		assertThat(errors.getErrorCount()).isEqualTo(1);			
		assertThat(errors.hasFieldErrors("pet")).isEqualTo(true);	
	}
	
	

}
