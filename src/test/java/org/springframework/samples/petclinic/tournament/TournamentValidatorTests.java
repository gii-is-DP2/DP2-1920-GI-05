package org.springframework.samples.petclinic.tournament;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Money;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.web.TournamentValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class TournamentValidatorTests {

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

	// Create new tournament Positive Case
	@Test
	void shouldValidateTournament() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tournament tournament = new Tournament();

		tournament.setApplyDate(LocalDate.of(2020, 10, 12));

		tournament.setCategory(category);
		tournament.setEndDate(LocalDate.of(2020, 12, 12));
		// tournament.setField();
		// tournament.setJugde();
		tournament.setLocation("Seville");

		tournament.setPetType(petType);

		tournament.setPrize(money);
		tournament.setName("McArthur Tournament 2020");
		tournament.setStartDate(LocalDate.of(2020, 12, 10));

		Validator validator = createValidator();
		Set<ConstraintViolation<Tournament>> constraintViolations = validator.validate(tournament);
		assertThat(constraintViolations.size()).isEqualTo(0);
		
		TournamentValidator tournamentValidator = new TournamentValidator();

		Errors errors = new BeanPropertyBindingResult(tournament, "tournament");
		tournamentValidator.validate(tournament, errors);
		assertThat(errors.getErrorCount()).isEqualTo(0);	
		assertThat(errors.hasErrors()).isEqualTo(false);	
	}
	
	// Create new tournament Negative Case: invalid name input
	@Test
	void shouldNotValidateName() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tournament tournament = new Tournament();

		tournament.setApplyDate(LocalDate.of(2020, 10, 12));

		tournament.setCategory(category);
		tournament.setEndDate(LocalDate.of(2020, 12, 12));
		// tournament.setField();
		// tournament.setJugde();
		tournament.setLocation("Seville");

		tournament.setPetType(petType);

		tournament.setPrize(money);
		tournament.setName("");
		tournament.setStartDate(LocalDate.of(2020, 12, 10));

		Validator validator = createValidator();
		Set<ConstraintViolation<Tournament>> constraintViolations = validator.validate(tournament);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Tournament> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");
	}
	
	// Create new tournament Negative Case: invalid location input
	@Test
	void shouldNotValidateLocation() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tournament tournament = new Tournament();

		tournament.setApplyDate(LocalDate.of(2020, 10, 12));

		Category category = new Category();
		category.setName("Agility");

		tournament.setCategory(category);
		tournament.setEndDate(LocalDate.of(2020, 12, 12));
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
		tournament.setStartDate(LocalDate.of(2020, 12, 10));

		Validator validator = createValidator();
		Set<ConstraintViolation<Tournament>> constraintViolations = validator.validate(tournament);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Tournament> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("location");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}
	
	// Create new tournament Negative Case: invalid apply Date input, can´t be after 
	// end or start date
	@Test
	void shouldNotValidateApplyDate() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tournament tournament = new Tournament();

		tournament.setApplyDate(LocalDate.of(2021, 10, 01));

		Category category = new Category();
		category.setName("Agility");

		tournament.setCategory(category);
		tournament.setEndDate(LocalDate.of(2020, 12, 12)); // tournament.setField(); //tournament.setJugde();
		tournament.setLocation("Seville");

		PetType petType = new PetType();
		petType.setName("Mouse");
		tournament.setPetType(petType);

		Money money = new Money();
		money.setAmount(100.00);
		money.setCurrency("$");

		tournament.setPrize(money);
		tournament.setName("Kendal 5 tournament");
		tournament.setStartDate(LocalDate.of(2020, 12, 10));

		TournamentValidator validator = new TournamentValidator();

		Errors errors = new BeanPropertyBindingResult(tournament, "tournament");
		validator.validate(tournament, errors);

		assertThat(errors.getErrorCount()).isEqualTo(2);
		assertThat(errors.hasFieldErrors("applyDate")).isEqualTo(true);
		assertThat(errors.hasFieldErrors("startDate")).isEqualTo(false);
		assertThat(errors.hasFieldErrors("endDate")).isEqualTo(false);	
	}
	
	// Create new tournament Negative Case: invalid start Date input, can´t be after end date 
	@Test
	void shouldNotValidateStartDate() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tournament tournament = new Tournament();

		tournament.setApplyDate(LocalDate.of(2020, 10, 01));

		Category category = new Category();
		category.setName("Agility");

		tournament.setCategory(category);
		tournament.setEndDate(LocalDate.of(2020, 12, 12)); // tournament.setField(); //tournament.setJugde();
		tournament.setLocation("Seville");

		PetType petType = new PetType();
		petType.setName("Mouse");
		tournament.setPetType(petType);

		Money money = new Money();
		money.setAmount(100.00);
		money.setCurrency("$");

		tournament.setPrize(money);
		tournament.setName("Kendal 5 tournament");
		tournament.setStartDate(LocalDate.of(2021, 12, 10));

		TournamentValidator validator = new TournamentValidator();

		Errors errors = new BeanPropertyBindingResult(tournament, "tournament");
		validator.validate(tournament, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.hasFieldErrors("applyDate")).isEqualTo(false);	
		assertThat(errors.hasFieldErrors("startDate")).isEqualTo(true);
		assertThat(errors.hasFieldErrors("endDate")).isEqualTo(false);	
	}
	
	// Create new tournament Negative Case: invalid end Date input, can´t be before start or apply Date	
	@Test
	void shouldNotValidateEndDate() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tournament tournament = new Tournament();

		tournament.setApplyDate(LocalDate.of(2020, 10, 01));

		Category category = new Category();
		category.setName("Agility");

		tournament.setCategory(category);
		tournament.setEndDate(LocalDate.of(2019, 12, 12)); // tournament.setField(); //tournament.setJugde();
		tournament.setLocation("Seville");

		PetType petType = new PetType();
		petType.setName("Mouse");
		tournament.setPetType(petType);

		Money money = new Money();
		money.setAmount(100.00);
		money.setCurrency("$");

		tournament.setPrize(money);
		tournament.setName("Kendal 5 tournament");
		tournament.setStartDate(LocalDate.of(2020, 12, 10));

		TournamentValidator validator = new TournamentValidator();

		Errors errors = new BeanPropertyBindingResult(tournament, "tournament");
		validator.validate(tournament, errors);

		assertThat(errors.getErrorCount()).isEqualTo(3);
		assertThat(errors.hasFieldErrors("applyDate")).isEqualTo(true);		
		assertThat(errors.hasFieldErrors("startDate")).isEqualTo(true);
		assertThat(errors.hasFieldErrors("endDate")).isEqualTo(true);		
	}
	
	// Create new tournament Negative Case: Invalid money currency 
	@Test
	void shouldNotValidateCurrency() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tournament tournament = new Tournament();

		tournament.setApplyDate(LocalDate.of(2020, 10, 01));

		Category category = new Category();
		category.setName("Agility");

		tournament.setCategory(category);
		tournament.setEndDate(LocalDate.of(2020, 12, 12)); // tournament.setField(); //tournament.setJugde();
		tournament.setLocation("Seville");

		PetType petType = new PetType();
		petType.setName("Mouse");
		tournament.setPetType(petType);

		Money money = new Money();
		money.setAmount(100.00);
		money.setCurrency("MONEY");

		tournament.setPrize(money);
		tournament.setName("Kendal 5 tournament");
		tournament.setStartDate(LocalDate.of(2020, 12, 10));

		TournamentValidator validator = new TournamentValidator();

		Errors errors = new BeanPropertyBindingResult(tournament, "tournament");
		validator.validate(tournament, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.hasFieldErrors("prize.currency")).isEqualTo(true);
	}
	
	// Create new tournament Negative Case: Invalid money amount with more than 2 fractions
	@Test
	void shouldNotValidatePrizeAmount1() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tournament tournament = new Tournament();

		tournament.setApplyDate(LocalDate.of(2020, 10, 01));

		Category category = new Category();
		category.setName("Agility");

		tournament.setCategory(category);
		tournament.setEndDate(LocalDate.of(2020, 12, 12)); // tournament.setField(); //tournament.setJugde();
		tournament.setLocation("Seville");

		PetType petType = new PetType();
		petType.setName("Mouse");
		tournament.setPetType(petType);

		Money money = new Money();
		money.setAmount(100.009);
		money.setCurrency("$");

		tournament.setPrize(money);
		tournament.setName("Kendal 5 tournament");
		tournament.setStartDate(LocalDate.of(2020, 12, 10));

		TournamentValidator validator = new TournamentValidator();

		Errors errors = new BeanPropertyBindingResult(tournament, "tournament");
		validator.validate(tournament, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.hasFieldErrors("prize.amount")).isEqualTo(true);
	}
	
	// Create new tournament Negative Case: invalid amount with more than 8 digits
	@Test
	void shouldNotValidatePrizeAmount2() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tournament tournament = new Tournament();

		tournament.setApplyDate(LocalDate.of(2020, 10, 01));

		Category category = new Category();
		category.setName("Agility");

		tournament.setCategory(category);
		tournament.setEndDate(LocalDate.of(2020, 12, 12)); // tournament.setField(); //tournament.setJugde();
		tournament.setLocation("Seville");

		PetType petType = new PetType();
		petType.setName("Mouse");
		tournament.setPetType(petType);

		Money money = new Money();
		money.setAmount(999999999.00);
		money.setCurrency("$");

		tournament.setPrize(money);
		tournament.setName("Kendal 5 tournament");
		tournament.setStartDate(LocalDate.of(2020, 12, 10));

		TournamentValidator validator = new TournamentValidator();

		Errors errors = new BeanPropertyBindingResult(tournament, "tournament");
		validator.validate(tournament, errors);
		assertThat(errors.getErrorCount()).isEqualTo(2);	
		assertThat(errors.hasFieldErrors("prize.amount")).isEqualTo(true);
	}
	
	
	// Create new tournament Negative Case: Null Category
	@Test
	void shouldNotValidateCategory() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tournament tournament = new Tournament();

		tournament.setApplyDate(LocalDate.of(2020, 10, 12));

		
		tournament.setEndDate(LocalDate.of(2020, 12, 12));
		// tournament.setField();
		// tournament.setJugde();
		tournament.setLocation("Seville");
		tournament.setName("Douglas Tournament");
		tournament.setPetType(petType);

		tournament.setPrize(money);
		tournament.setStartDate(LocalDate.of(2020, 12, 10));

		
		TournamentValidator validator = new TournamentValidator();

		Errors errors = new BeanPropertyBindingResult(tournament, "tournament");
		validator.validate(tournament, errors);
		assertThat(errors.getErrorCount()).isEqualTo(1);	
		assertThat(errors.hasFieldErrors("category")).isEqualTo(true);
	}
	


}
