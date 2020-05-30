package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Report;
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
		  
		  if (points!= null && (points < 0 || points > 100)) {
		  errors.rejectValue("points", REQUIRED + " beetwen 0 and 100", REQUIRED + " beetwen 0 and 100"); }  
 
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Report.class.isAssignableFrom(clazz);
	}

}
