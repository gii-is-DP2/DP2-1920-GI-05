package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ReportValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Report report = (Report) obj;		
		Integer points = report.getPoints();
		String comments	= report.getComments();

		  if (points == null) {
		  errors.rejectValue("points", REQUIRED, REQUIRED); }
		  
		  if (comments.isEmpty() ) {
		  errors.rejectValue("comments", REQUIRED, REQUIRED); }  
 
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Report.class.isAssignableFrom(clazz);
	}

}
