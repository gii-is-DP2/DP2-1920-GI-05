/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.VetService;
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
