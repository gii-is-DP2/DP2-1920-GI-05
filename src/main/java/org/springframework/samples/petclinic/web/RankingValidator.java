package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Ranking;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RankingValidator implements Validator {

	private static final String REQUIRED = "Required";

	@Override
	public void validate(Object obj, Errors errors) {
		Ranking ranking = (Ranking) obj;
		

		// List validation

		if (ranking.isNew() && ranking.getPodium() == null) {
			errors.rejectValue("type", REQUIRED, REQUIRED);
		}

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Ranking.class.isAssignableFrom(clazz);
	}

}
