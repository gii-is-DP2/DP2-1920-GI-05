package org.springframework.samples.petclinic.web;


import org.springframework.samples.petclinic.model.Application;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ApplicationValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Application application = (Application) obj;		

		  if (application.getPet() == null) {
		  errors.rejectValue("pet", REQUIRED, REQUIRED); }
		  
		  if (application.getTournament() == null) {
		  errors.rejectValue("tournament", REQUIRED, REQUIRED); }	 	
		  
		  if (application.getOwner() == null) {
			  errors.rejectValue("owner", REQUIRED, REQUIRED); }	 	

		  if (application.getStatus().isEmpty()){
		  errors.rejectValue("status", REQUIRED, REQUIRED); }	 	
		  		
		  if (!application.getStatus().isEmpty() && validateStatus(application.getStatus()) == false) { 			  
			  errors.rejectValue("status", "Status has to be rejected, pending or accepted", "Status has to be rejected, pending or accepted"); 
			  }
	}
	
	public boolean validateStatus(String s) {
		boolean res;
		res = s.equals("REJECTED")  || s.equals("ACCEPTED") || s.equals("PENDING");					
		return res;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Application.class.isAssignableFrom(clazz);
	}

}
