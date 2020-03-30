package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
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
		String location = tournament.getLocation();
		String currency = tournament.getPrize().getCurrency();
		
		// name validation
		if (!StringUtils.hasLength(name) || name.length() > 50 || name.length() < 3) {
			errors.rejectValue("name",  REQUIRED + "and must be between 3 and 50 characters",
					REQUIRED + "and must be between 3 and 50 characters");
		}

		
		  // birth date validation if
		  if(tournament.getApplyDate() !=null && tournament.getStartDate() != null && tournament.getApplyDate().isAfter(tournament.getStartDate())) {
		  errors.rejectValue("applyDate", "Apply date can not be after start date",
		  "Apply date can not be after start date"); }
		  
		  if (tournament.getApplyDate() !=null && tournament.getEndDate() != null && tournament.getApplyDate().isAfter(tournament.getEndDate())) {
		  errors.rejectValue("applyDate", " Apply date can not be after end date", DATE
		  + " apply date can not be after end date"); }
		  
		  
		  if (tournament.getStartDate() !=null && tournament.getEndDate() != null && tournament.getStartDate().isAfter(tournament.getEndDate())) {
		  errors.rejectValue("startDate", " Start date can not be after end date", DATE
		  + " start date can not be after end date"); }
		  
		  if (location.isEmpty() ) {
		  errors.rejectValue("location", REQUIRED, REQUIRED); }
		  
		  LocalDate now = LocalDate.now();
		  
		  if (tournament.getApplyDate() != null && (tournament.getApplyDate().isAfter(now) || tournament.getApplyDate().equals(now))) {
		  errors.rejectValue("applyDate", REQUIRED, REQUIRED); }
		  
		  if (tournament.getApplyDate() == null) {
		  errors.rejectValue("applyDate", REQUIRED, REQUIRED); }
		  
		  if (tournament.getStartDate() == null) {
		  errors.rejectValue("startDate", REQUIRED, REQUIRED); }
		  
		  if (tournament.getEndDate() == null) {
		  errors.rejectValue("endDate", REQUIRED, REQUIRED); }
		  
		  if (tournament.isNew() && tournament.getPetType() == null) {
		  errors.rejectValue("petType", REQUIRED, REQUIRED); }
		  
		  if (tournament.isNew() && tournament.getCategory() == null) {
		  errors.rejectValue("category", REQUIRED, REQUIRED); }	  
		
		  if (tournament.getPrize().getAmount() == null) {
		  errors.rejectValue("prize.amount", REQUIRED, REQUIRED); }
		  
		  if (tournament.getPrize().getAmount() != null && tournament.getPrize().getAmount() <= 0) {
		  errors.rejectValue("prize.amount", "The amount can not be negative or 0", "The amount can not be negative or 0"); }
		  
		  if (tournament.getPrize().getAmount() != null && countIntegers(tournament.getPrize().getAmount()) > 7) {
		  errors.rejectValue("prize.amount", "The amount can not have more than 7 integers", "The amount can not have more than 7 integers"); }
		  
		  if (tournament.getPrize().getAmount() != null && countFractions(tournament.getPrize().getAmount()) > 2) {
		  errors.rejectValue("prize.amount", "The amount can not have more than 2 fractions", "The amount can not have more than 2 fractions"); }
		  
		  if (currency.isEmpty()) {
		  errors.rejectValue("prize.currency", REQUIRED, REQUIRED); }
		  
		  Boolean validCurrency = (currency.equals( "$")  ||  currency.equals( "€") ||  currency.equals( "EUR")  ||  currency.equals( "USD"));
		  
		  if (!currency.isEmpty()  && (!validCurrency) ) {
		  errors.rejectValue("prize.currency", "The currency has to be a valid", "The currency has to be a valid"); }
		 
		 



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
	

	@Override
	public boolean supports(Class<?> clazz) {
		return Tournament.class.isAssignableFrom(clazz);
	}

}
