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
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ApplicationTest {
	
	private Owner owner;
	private Tournament tournament;
	
	
	@BeforeEach
	void setup() {
		
		owner = new Owner();
		owner.setFirstName("John Doe");
		
		tournament = new Tournament();
		tournament.setName("Quarentine's Tournament");
		
	}
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	

	@Test
	void shouldNotValidateStatus() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Application app = new Application();
		app.setCreditCard("4065972557141631");
		app.setMoment(LocalDate.of(2000, 10, 12));
		app.setOwner(owner);
		app.setStatus("");
		app.setTournament(tournament);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Application>> constraintViolations = validator.validate(app);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Application> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("status");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
		
	}
	
	@Test
	void shouldNotValidateCreditCard() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Application app = new Application();
		app.setCreditCard("49721");
		app.setMoment(LocalDate.of(2000, 10, 12));
		app.setOwner(owner);
		app.setStatus("REJECTED");
		app.setTournament(tournament);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Application>> constraintViolations = validator.validate(app);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Application> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("creditCard");
		assertThat(violation.getMessage()).isEqualTo("must be a valid credit card");
		
	}
	
	@Test
	void shouldNotValidateTournament() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Application app = new Application();
		app.setCreditCard("4065972557141631");
		app.setMoment(LocalDate.of(2000, 10, 12));
		app.setOwner(owner);
		app.setStatus("REJECTED");
		app.setTournament(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Application>> constraintViolations = validator.validate(app);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Application> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("tournament");
		assertThat(violation.getMessage()).isEqualTo("application must have a tournament");
		
	}
	
	

}
