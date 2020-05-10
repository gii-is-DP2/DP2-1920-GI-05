package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Field;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FieldValidator implements Validator {

	private static final String REQUIRED = "Required";

	@Override
	public void validate(Object obj, Errors errors) {
		Field field = (Field) obj;
		String name = field.getName();
		
		Double width = field.getWidth();
		Double lenght = field.getLenght();
		
		// name validation
		if (!StringUtils.hasLength(name) || name.length() > 50 || name.length() < 3) {
			errors.rejectValue("name", REQUIRED + " and must be between 3 and 50 characters",
					REQUIRED + " and must be between 3 and 50 characters");
		}
		
		//width not null
		if (width ==null) {
			errors.rejectValue("width", REQUIRED, REQUIRED);
		}
		
		//width 5 integers
		if (width != null && countIntegers(width) > 5) {		
				errors.rejectValue("width", "Must be less than 5 integers", "Must be less than 5 integers");
			}
		
		//width 2 fractions
		if (width != null && countFractions(width) > 2) {		
				errors.rejectValue("width", "Must be less than  2 fractions", "Must be less than 2 fractions");
			}

		//width not negative
		if (width != null && width <= 0) {		
				errors.rejectValue("width", "The breadth can not be negative or 0",
						"The breadth can not be negative or 0");
			}
		
		//lenght not null
		if (lenght == null) {
			errors.rejectValue("lenght", REQUIRED, REQUIRED);
		}
		
		//lenght not null
		if (lenght != null && countIntegers(lenght) > 5) {		
				errors.rejectValue("lenght", "Must be less than 5 integers", "Must be less than 5 integers");
		}

		//lenght 2 fractions
		if (lenght != null && countFractions(lenght) > 2) {		
				errors.rejectValue("lenght", "Must be less than  2 fractions", "Must be less than 2 fractions");
			}

		//lenght not negative
		if (lenght != null && lenght <= 0) {		
				errors.rejectValue("lenght", "The lenght can not be negative or 0",
						"The lenght can not be negative or 0");
			}
		
	  //photo not null
		if (field.getPhotoURL().isEmpty()) {
			errors.rejectValue("photoURL", REQUIRED, REQUIRED);
		}

    //photo is url
		if (!UrlUtils.isAbsoluteUrl(field.getPhotoURL())) {
			errors.rejectValue("photoURL", "Must be an url", "Must be an url");
		}

	}


	@Override
	public boolean supports(Class<?> clazz) {
		return Field.class.isAssignableFrom(clazz);
	}

	public int countIntegers(Double d) {
		Integer intValue = d.intValue();		
		int count = intValue.toString().length();
		
		return count;		
	}

	
	public int countFractions(Double d) {
		
		String text = Double.toString(Math.abs(d));
		int integerPlaces = text.indexOf('.');
		int count = text.length() - integerPlaces - 1;		
		return count;		
	}


	

}
