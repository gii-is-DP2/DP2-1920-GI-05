package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Category;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CategoryValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Category category = (Category) obj;
		String name = category.getName();
		// name validation
		if (!StringUtils.hasLength(name) || name.length() > 50 || name.length() < 3) {
			errors.rejectValue("name", "The name must be between 3 and 50 characters",
					"The name must be between 3 and 50 characters");
		}

		/* type validation
		if (category.getName() == null) {
			errors.rejectValue("name", REQUIRED, REQUIRED);
		}*/

	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Category.class.isAssignableFrom(clazz);
	}

}
