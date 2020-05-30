package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Component;

@Component
public class OwnerFormatter implements Formatter<Owner> {

	private final OwnerService ownerService;

	@Autowired
	public OwnerFormatter(OwnerService ownerService) {
		this.ownerService = ownerService;
	}

	@Override
	public String print(Owner owner, Locale locale) {
		return owner.getLastName() ;
	}

	@Override
	public Owner parse(String text, Locale locale) throws ParseException {
		Collection<Owner> findOwner = this.ownerService.findAllOwners();
		for (Owner owner : findOwner) {
			if (owner.getLastName().equals(text)) {
				return owner;
			}
		}
		throw new ParseException("Owner not found: " + text, 0);
	}

}
