package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.web.TournamentValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class TournamentTests {

	// OJO Aquí habria que crear tournamentValidator
	/*
	 * private TournamentValidator createValidator() { LocalValidatorFactoryBean
	 * localValidatorFactoryBean = new LocalValidatorFactoryBean();
	 * localValidatorFactoryBean.afterPropertiesSet(); return (TournamentValidator)
	 * localValidatorFactoryBean.getValidator(); }
	 */

	private Money money;
	private Category category;
	private PetType petType;

	@BeforeEach
	void setup() {

		category = new Category();
		category.setName("Agility");

		money = new Money();
		money.setAmount(100.00);
		money.setCurrency("$");

		petType = new PetType();
		petType.setName("Mouse");

	}

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateName() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tournament tournament = new Tournament();

		tournament.setApplyDate(LocalDate.of(2000, 10, 12));

		tournament.setCategory(category);
		tournament.setEndDate(LocalDate.of(2000, 12, 12));
		// tournament.setField();
		// tournament.setJugde();
		tournament.setLocation("Seville");

		tournament.setPetType(petType);

		tournament.setPrize(money);
		tournament.setName("");
		tournament.setStartDate(LocalDate.of(2000, 12, 10));

		Validator validator = createValidator();
		Set<ConstraintViolation<Tournament>> constraintViolations = validator.validate(tournament);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Tournament> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");
	}

	@Test
	void shouldNotValidateLocation() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tournament tournament = new Tournament();

		tournament.setApplyDate(LocalDate.of(2000, 10, 12));

		Category category = new Category();
		category.setName("Agility");

		tournament.setCategory(category);
		tournament.setEndDate(LocalDate.of(2000, 12, 12));
		// tournament.setField();
		// tournament.setJugde();
		tournament.setLocation("");

		PetType petType = new PetType();
		petType.setName("Mouse");
		tournament.setPetType(petType);

		Money money = new Money();
		money.setAmount(100.00);
		money.setCurrency("$");

		tournament.setPrize(money);
		tournament.setName("Kendall 3 tournament");
		tournament.setStartDate(LocalDate.of(2000, 12, 10));

		Validator validator = createValidator();
		Set<ConstraintViolation<Tournament>> constraintViolations = validator.validate(tournament);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Tournament> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("location");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

	/*
	 * @Test void shouldNotValidateDate() {
	 * 
	 * LocaleContextHolder.setLocale(Locale.ENGLISH); Tournament tournament = new
	 * Tournament();
	 * 
	 * tournament.setApplyDate(LocalDate.of(2001, 12, 9));
	 * 
	 * Category category = new Category(); category.setName("Agility");
	 * 
	 * tournament.setCategory(category); tournament.setEndDate(LocalDate.of(2000,
	 * 12, 12)); // tournament.setField(); //tournament.setJugde();
	 * tournament.setLocation("Seville");
	 * 
	 * PetType petType = new PetType(); petType.setName("Mouse");
	 * tournament.setPetType(petType);
	 * 
	 * Money money = new Money(); money.setAmount(100.00); money.setCurrency("$");
	 * 
	 * tournament.setPrize(money); tournament.setName("Kendal 5 tournament");
	 * tournament.setStartDate(LocalDate.of(2000, 12, 10));
	 * 
	 * TournamentValidator validator = new TournamentValidator();
	 * 
	 * Set<ConstraintViolation<Tournament>> constraintViolations =
	 * validator.validate(tournament, errors);
	 * 
	 * assertThat(constraintViolations.size()).isEqualTo(1);
	 * ConstraintViolation<Tournament> violation =
	 * constraintViolations.iterator().next();
	 * assertThat(violation.getPropertyPath().toString()).isEqualTo("applyDate");
	 * assertThat(violation.getMessage()).isEqualTo("size must be betweeen 3 and 50"
	 * ); }
	 */

}
