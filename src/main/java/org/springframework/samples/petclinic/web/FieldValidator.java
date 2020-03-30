package org.springframework.samples.petclinic.web;

import java.text.NumberFormat;
import java.text.ParsePosition;

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
		// name validation
		if (!StringUtils.hasLength(name) || name.length() > 50 || name.length() < 3) {
			errors.rejectValue("name", REQUIRED + " and must be between 3 and 50 characters",
					REQUIRED + " and must be between 3 and 50 characters");
		}
		
		if (field.getBreadth().isEmpty()) {
			errors.rejectValue("breadth", REQUIRED, REQUIRED);
		}

		if (!field.getBreadth().isEmpty() && isNumeric(field.getBreadth()) == true) {
			if (countIntegers(field.getBreadth()) > 5) {
				errors.rejectValue("breadth", "Must be less than 5 integers", "Must be less than 5 integers");
			}
		}

		if (!field.getBreadth().isEmpty() && isNumeric(field.getBreadth()) == true) {
			if (countFractions(field.getBreadth()) > 2) {
				errors.rejectValue("breadth", "Must be less than  2 fractions", "Must be less than 2 fractions");
			}
		}

		if (!field.getBreadth().isEmpty() && isNumeric(field.getBreadth()) == true) {
			if (Double.parseDouble(field.getBreadth()) <= 0) {
				errors.rejectValue("breadth", "The breadth can not be negative or 0",
						"The breadth can not be negative or 0");
			}
		}
		
		if (!field.getBreadth().isEmpty()) {
			if (isNumeric(field.getBreadth()) == false) {
				errors.rejectValue("breadth", "Has to be numeric", "Has to be numeric");
			}
		}
		
		if (field.getBreadth().contains(",")) {			
			errors.rejectValue("breadth", "Please use the sign '.' for decimal numbers no ',' ", "Please use the sign '.' for decimal numbers no ',' ");
		}
		
		if (field.getLenght().isEmpty()) {
			errors.rejectValue("lenght", REQUIRED, REQUIRED);
			}

		if (!field.getLenght().isEmpty() && isNumeric(field.getLenght()) == true) {
			if (countIntegers(field.getLenght()) > 5)
				errors.rejectValue("lenght", "Must be less than 5 integers", "Must be less than 5 integers");
		}

		if (!field.getLenght().isEmpty() && isNumeric(field.getLenght()) == true ) {
			if (countFractions(field.getLenght()) > 2) {
				errors.rejectValue("lenght", "Must be less than  2 fractions", "Must be less than 2 fractions");
			}
		}

		if (!field.getLenght().isEmpty() && isNumeric(field.getLenght()) == true ) {
			if (Double.parseDouble(field.getLenght()) <= 0) {
				errors.rejectValue("lenght", "The lenght can not be negative or 0",
						"The lenght can not be negative or 0");
			}
		}
				
		
		if (!field.getLenght().isEmpty()) {
			if (isNumeric(field.getLenght()) == false) {
				errors.rejectValue("lenght", "Has to be numeric", "Has to be numeric");
			}
		}
		
		if (field.getLenght().contains(",")) {			
				errors.rejectValue("lenght", "Please use the sign '.' for decimal numbers no ',' ", "Please use the sign '.' for decimal numbers no ',' ");
			}
		

		if (field.getPhotoURL().isEmpty()) {
			errors.rejectValue("photoURL", REQUIRED, REQUIRED);
		}

		if (!UrlUtils.isAbsoluteUrl(field.getPhotoURL())) {
			errors.rejectValue("photoURL", "Must be an url", "Must be an url");
		}

	}


	@Override
	public boolean supports(Class<?> clazz) {
		return Field.class.isAssignableFrom(clazz);
	}

	public static int countIntegers(String s) {
		int count = 0;
		String r = s;
		if (s.contains("-")) {
			r = s.substring(1);
		}
		Double d = Double.parseDouble(r);
		Integer i = d.intValue();
		count = i.toString().length();

		return count;
	}

	public int countFractions(String s) {
		int count = 0;		
			int integerPlaces = 0;
			if(s.contains(".")) {
			integerPlaces = s.indexOf('.');
			}
			if(s.contains(",")) {
			integerPlaces = s.indexOf(',');
			}
			count = s.length() - integerPlaces - 1;
		
		return count;
	}

	public static boolean isNumeric(String str) {
		  NumberFormat formatter = NumberFormat.getInstance();
		  ParsePosition pos = new ParsePosition(0);
		  formatter.parse(str, pos);
		  return str.length() == pos.getIndex();
		}
	

}
