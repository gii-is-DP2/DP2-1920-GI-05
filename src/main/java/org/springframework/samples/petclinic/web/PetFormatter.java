package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Component;


@Component
public class PetFormatter implements Formatter<Pet> {

	private final PetService petService;

	@Autowired
	public PetFormatter(PetService petService) {
		this.petService = petService;
	}

	@Override
	public String print(Pet pet, Locale locale) {
		return pet.getName();
	}

	@Override
	public Pet parse(String text, Locale locale) throws ParseException {
		Collection<Pet> findPets = this.petService.findAllPets();
		for (Pet pet : findPets) {
			if (pet.getName().equals(text)) {
				return pet;
			}
		}
		throw new ParseException("pet not found: " + text, 0);
	}
	
}
