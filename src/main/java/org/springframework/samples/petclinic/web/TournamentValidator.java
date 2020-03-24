package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.springframework.samples.petclinic.model.Money;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TournamentValidator implements Validator {

	private static final String REQUIRED = "required";
	private static final String DATE = "typeMismatch.date";

	@Override
	public void validate(Object obj, Errors errors) {
		Tournament tournament = (Tournament) obj;		
		String name = tournament.getName();
		// name validation
		if (!StringUtils.hasLength(name) || name.length() > 50 || name.length() < 3) {
			errors.rejectValue("name", REQUIRED + " and between 3 and 50 characters",
					REQUIRED + " and between 3 and 50 character");
		}

		// type location
		if (tournament.getLocation() == null) {
			errors.rejectValue("location", REQUIRED, REQUIRED);
		}

		// birth date validation
		if (tournament.getApplyDate().isAfter(tournament.getStartDate())) {
			errors.rejectValue("applyDate", DATE, DATE);
		}

		if (tournament.getApplyDate().isAfter(tournament.getEndDate())) {
			errors.rejectValue("applyDate", DATE, DATE);
		}

		if (tournament.getApplyDate() == null) {
			errors.rejectValue("applyDate", REQUIRED, REQUIRED);
		}

		if (tournament.getStartDate().isAfter(tournament.getEndDate())) {
			errors.rejectValue("startDate", DATE, DATE);
		}

		if (tournament.getStartDate() == null) {
			errors.rejectValue("startDate", REQUIRED, REQUIRED);
		}

		if (tournament.getEndDate() == null) {
			errors.rejectValue("endDate", REQUIRED, REQUIRED);
		}

		if (tournament.getPrize().getAmount() == null) {
			errors.rejectValue("amount", REQUIRED, REQUIRED);
		}

		if (tournament.getPrize().getCurrency() == null) {
			errors.rejectValue("currency", REQUIRED, REQUIRED);
		}

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Tournament.class.isAssignableFrom(clazz);
	}

}
