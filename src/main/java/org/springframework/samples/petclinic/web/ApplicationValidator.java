package org.springframework.samples.petclinic.web;


import org.springframework.samples.petclinic.model.Application;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ApplicationValidator implements Validator {

	private static final String REQUIRED = "required";
	private static final String DATE = "typeMismatch.date";

	@Override
	public void validate(Object obj, Errors errors) {
		Application application = (Application) obj;		

		  if (application.isNew() && application.getPet() == null) {
		  errors.rejectValue("pet", REQUIRED, REQUIRED); }
		  
		  if (application.isNew() && application.getTournament() == null) {
		  errors.rejectValue("tournament", REQUIRED, REQUIRED); }	 	
		  
		  if (application.isNew() && application.getOwner() == null) {
			  errors.rejectValue("owner", REQUIRED, REQUIRED); }	 	

		  if (application.isNew() && application.getStatus().isEmpty()){
		  errors.rejectValue("status", REQUIRED, REQUIRED); }	 	
		  
		  if (!application.getStatus().isEmpty() && !validateStatus(application.getStatus())) {
		  errors.rejectValue("status", "Status has to be rejected, pending or accepted", "Status has to be rejected, pending or accepted"); 
		  }	 	
 
	


	}
	
	public boolean validateStatus(String s) {
		boolean res = false;
		if(s=="REJECTED" || s =="ACCEPTED" || s == "PENDING") {
			res = true;
		}
		return res;
	}


	@Override
	public boolean supports(Class<?> clazz) {
		return Application.class.isAssignableFrom(clazz);
	}

}
