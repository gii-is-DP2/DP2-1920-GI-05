package org.springframework.samples.petclinic.report;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.web.ReportValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ReportValidatorTests {

	private Judge judge; 
	private Tournament tournament;
	private Pet pet;
	
	@BeforeEach
	void setup() {

		judge = new Judge();
		
		tournament = new Tournament();
		
		pet = new Pet();
			
	}
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	// Create new Report Positive Case: 
	@Test
	void shouldValidateReport() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Report report = new Report();

		report.setComments("Good perfomances");
		report.setPoints(80);
		
		report.setJudge(judge);
		report.setPet(pet);
		report.setTournament(tournament);
	

		Validator validator = createValidator();
		Set<ConstraintViolation<Report>> constraintViolations = validator.validate(report);
		assertThat(constraintViolations.size()).isEqualTo(0);
		
		ReportValidator reportValidator = new ReportValidator();

		Errors errors = new BeanPropertyBindingResult(report, "report");
		reportValidator.validate(report, errors);
		assertThat(errors.getErrorCount()).isEqualTo(0);	
		assertThat(errors.hasErrors()).isEqualTo(false);	
	}
	
	// Create new Report Negative Case: Blank Comment
	@Test
	void shouldNotValidateReportBlankComment() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Report report = new Report();

		report.setComments("");
		report.setPoints(99);
		
		report.setJudge(judge);
		report.setPet(pet);
		report.setTournament(tournament);
	

		Validator validator = createValidator();
		Set<ConstraintViolation<Report>> constraintViolations = validator.validate(report);
		ConstraintViolation<Report> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("comments");
		assertThat(constraintViolations.size()).isEqualTo(1);		
		
		ReportValidator reportValidator = new ReportValidator();
		Errors errors = new BeanPropertyBindingResult(report, "report");
		
		reportValidator.validate(report, errors);
		assertThat(errors.getErrorCount()).isEqualTo(1);	
		assertThat(errors.hasErrors()).isEqualTo(true);	
	}
	
	// Create new Report Negative Case: Blank points
	@Test
	void shouldNotValidateReportBlankPoints() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Report report = new Report();

		report.setComments("Nothing special");
		report.setPoints(null);
		
		report.setJudge(judge);
		report.setPet(pet);
		report.setTournament(tournament);
	

		Validator validator = createValidator();
		Set<ConstraintViolation<Report>> constraintViolations = validator.validate(report);
		assertThat(constraintViolations.size()).isEqualTo(0);
				
		ReportValidator reportValidator = new ReportValidator();

		Errors errors = new BeanPropertyBindingResult(report, "report");
		reportValidator.validate(report, errors);
		assertThat(errors.getErrorCount()).isEqualTo(1);	
		assertThat(errors.hasErrors()).isEqualTo(true);	
	}
	
	// Create new Report Negative Case: Negative points
	@Test
	void shouldNotValidateReportLowerPoints() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Report report = new Report();

		report.setComments("No comments");
		report.setPoints(-100);
		
		report.setJudge(judge);
		report.setPet(pet);
		report.setTournament(tournament);
	

		Validator validator = createValidator();
		Set<ConstraintViolation<Report>> constraintViolations = validator.validate(report);
		ConstraintViolation<Report> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("points");
		assertThat(constraintViolations.size()).isEqualTo(1);
				
		ReportValidator reportValidator = new ReportValidator();

		Errors errors = new BeanPropertyBindingResult(report, "report");
		reportValidator.validate(report, errors);
		assertThat(errors.getErrorCount()).isEqualTo(1);	
		assertThat(errors.hasErrors()).isEqualTo(true);	
	}
	
	// Create new Report Negative Case: Points higher than 100
	@Test
	void shouldNotValidateReportHigherPoints() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Report report = new Report();

		report.setComments("Great job");
		report.setPoints(101);
		
		report.setJudge(judge);
		report.setPet(pet);
		report.setTournament(tournament);
	

		Validator validator = createValidator();
		Set<ConstraintViolation<Report>> constraintViolations = validator.validate(report);
		ConstraintViolation<Report> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("points");
		assertThat(constraintViolations.size()).isEqualTo(1);
				
		ReportValidator reportValidator = new ReportValidator();

		Errors errors = new BeanPropertyBindingResult(report, "report");
		reportValidator.validate(report, errors);
		assertThat(errors.getErrorCount()).isEqualTo(1);	
		assertThat(errors.hasErrors()).isEqualTo(true);	
	}
	
	
	
}
